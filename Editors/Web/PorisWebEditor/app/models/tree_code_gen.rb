class TreeCodeGen < CodeGen
  def standard_fields(model, include_timestamps=false)
    fields = model.attr_order.*.to_s & model.content_columns.*.name
    fields -= %w{created_at updated_at created_on updated_on deleted_at} unless include_timestamps
    fields.reject! { |f| model.never_show? f }
    fields
  end

  def to_code(init,s,doneelems)
    if init
      ret="<?xml version=\"1.0\" encoding=\"UTF-8\"?>
      <sub-systems-v4>"
    else
      ret=""
    end
    # Parche: de momento la cabecera no la incluimos en el código generado
    # fin parche
    if (!doneelems.include?(s))
      #Generamos el código propio
      doneelems+=[s]
      s.destinations.each{|ss|
        retaux,doneelems=self.to_code(false,ss,doneelems)
        ret+=retaux
      }
      onlyarray=s.class.my_attributes + [:id]
      ret+=s.to_xml(:skip_instruct => true, :only => onlyarray,
        :include =>{
          :destinations => {:only => [:id]},
          :labels=>{:only => [:name],:include=>[:scope_kind]},
          :node_attributes=>{:only => [:name,:content,:visibility]},
        })
    end
    if init
      ret+="</sub-systems-v4>"
    end
    return ret,doneelems
  end

end
