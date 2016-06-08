class OpmsCodeGen < CodeGen

  def to_code(init,s,doneelems)
    ret=""
    if (!doneelems.include?(s))
      #Generamos el código propio
      doneelems+=[s]
      ret="#{s.class.to_s} s#{s.id.to_s} = new #{s.class.to_s}(\"#{s.name}\");\n"
      ret+="s#{s.id.to_s}.setId(#{s.id});\n"
      if (s.has_label?(self))
        ret+="s#{s.id.to_s}.setLabel(\"#{s.to_s_scope_kind(self)}\");\n"
      end
    end
    #Generamos el código de los subsistemas
    s.sub_systems.each {|ss|
        retaux,doneelems=self.to_code(false,ss,doneelems)
        ret+=retaux
        ret+="s#{s.id.to_s}.addSubSystem(s#{ss.id.to_s});\n"
    }
    #Generamos el código de los valores
    s.values.each {|v|
      if (!doneelems.include?(v))
        doneelems+=[v]
        ret+="#{v.class.to_s} v#{v.id.to_s} = new #{v.class.to_s}(\"#{v.name}\""
        if (v.class==ValueDoubleRange)
          ret+=",#{v.default_float.to_s},#{v.rangemin.to_s},#{v.rangemax.to_s}"
        else
          if (v.class==ValueString)
            ret+=",\"#{v.default_string.to_s}\""
          else
            if (v.class==ValueDateRange)
              ret+=",#{v.default_date.to_s},#{v.date_min.to_s},#{v.date_max.to_s}"
            end
          end
        end
        ret+=");\n"
        ret+="v#{v.id.to_s}.setId(#{v.id});\n"
        if (v.has_label?(self))
          ret+="v#{v.id.to_s}.setLabel(\"#{v.to_s_scope_kind(self)}\");\n"
        end
      end
      #Los anadimos al subsistema actual
      ret+="s#{s.id.to_s}.addValue(v#{v.id.to_s});\n"
    }
    #Generamos el código de los modos
    default_mode_set=false;
    first_mode=nil;
    s.modes.each {|m|
      if (first_mode==nil)
        first_mode=m
      end
      if (!doneelems.include?(m))
        doneelems+=[m]
        ret+="Mode m#{m.id.to_s}= new Mode(\"#{m.name}\");\n"
        ret+="m#{m.id.to_s}.setId(#{m.id});\n"
        if (m.has_label?(self))
          ret+="m#{m.id.to_s}.setLabel(\"#{m.to_s_scope_kind(self)}\");\n"
        end
        #Anyadimos los valores a los modos
        #Los linkamos a los valores
        default_value_set=false;
        first_value=nil;
        m.values.each {|mv|
            if (first_value==nil)
              first_value=mv;
            end
            ret+="m#{m.id.to_s}.addValue(v#{mv.id.to_s});\n"
            if !default_value_set
              if (m.default_value==mv)
                default_value_set=true
                ret+="m#{m.id.to_s}.setDefaultValue(v#{mv.id.to_s});\n"
              end
            end
        }
        if (!default_value_set and first_value!=nil)
           ret+="m#{m.id.to_s}.setDefaultValue(v#{first_value.id.to_s});\n"
        end
        #Los linkamos a los submodos
        first_sub_mode=nil
        default_sub_mode_set=false
        m.sub_modes.each {|msm|
            if (first_sub_mode==nil)
              first_sub_mode=msm
            end
            ret+="m#{m.id.to_s}.addSubMode(m#{msm.id.to_s});\n"
            if (msm==m.default_mode)
              ret+="m#{m.id.to_s}.setDefaultMode(m#{msm.id.to_s});\n"
              default_sub_mode_set=true
            end
        }
        if (!default_sub_mode_set and first_sub_mode!=nil)
          ret+="m#{m.id.to_s}.setDefaultMode(m#{first_sub_mode.id.to_s});\n"
        end
      end
      #Los anadimos al subsistema actual
      ret+="s#{s.id.to_s}.addMode(m#{m.id.to_s});\n"
      #Seleccionamos el modo por defecto como el primero
      if not default_mode_set
        if s.default_mode==m
          default_mode_set=true
          ret+="s#{s.id.to_s}.setDefaultMode(m#{m.id.to_s});\n"
        end
      end
    }
    if (!default_mode_set and first_mode!=nil)
      ret+="s#{s.id.to_s}.setDefaultMode(m#{first_mode.id.to_s});\n"
    end
    if (init)
      ret+="#{s.class.to_s} s=s#{s.id.to_s};"
    end
    return ret,doneelems
  end

end
