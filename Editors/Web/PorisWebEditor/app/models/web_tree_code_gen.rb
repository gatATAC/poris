class WebTreeCodeGen < CodeGen

  def to_code(init,s,doneelems)
    #allowed=s.view_permitted?
    allowed=true
    if (allowed)
      ret=to_code_int(s)
      return ret,[doneelems,s.subtree]
    else
      "Not allowed operation"
    end
  end

  def to_code_embedded(s)
    return to_code(s)
  end

  def to_code_int(s)
    #allowed=s.view_permitted?
    allowed=true
    if allowed
      ret="<treeview title=\""+s.name+" Tree\">\n"
      if (s.class==Library)
        ret+=get_tree_data_xml_lb(s){|n|
          #link_to(n.name,{:controller=>'nodes', :action=>'show', :id=>n.id}, :target => "main") }
          "<a href='/nodes/"+n.id.to_s+"' target='main'>"+n.name+"</a>"
        }
      else
        if (s.class==Mode)
          ret+=get_tree_data_xml_md(s){|n|
            #link_to(n.name,{:controller=>'nodes', :action=>'show', :id=>n.id}, :target => "main") }
            "<a href='/nodes/"+n.id.to_s+"' target='main'>"+n.name+"</a>"
          }
        else
          ret+=get_tree_data_xml_ss(s){|n|
            #link_to(n.name,{:controller=>'nodes', :action=>'show', :id=>n.id}, :target => "main") }
            "<a href='/nodes/"+n.id.to_s+"' target='main'>"+n.name+"</a>"
          }
        end
      end
      ret+="</treeview>"
      return ret
    else
      "Not allowed operation 2"
    end
  end

  def get_tree_data_xml_lb(shownode)
    if (shownode.has_children?)
      ret="<folder title=\""+shownode.name+"\" code=\""+shownode.id.to_s+"\""+" img=\""+shownode.node_type.img_link+"\" action=\""+self.id.to_s+"\" >\n"
      shownode.sub_libs.each {|n|
        ret+=get_tree_data_xml_lb(n)
      }
      shownode.systems.each {|n|
        ret+=get_tree_data_xml_ss(n)
      }
      ret+="</folder>\n"
    else
      ret="<leaf title=\""+shownode.name+"\" code=\""+shownode.id.to_s+"\""+" img=\""+shownode.node_type.img_link+"\" action=\""+self.id.to_s+"\" />\n"
    end
    return ret
  end

  def get_tree_data_xml_ss(shownode)
    if (shownode.has_children?)
      ret="<folder title=\""+shownode.name+"\" code=\""+shownode.id.to_s+"\""+" img=\""+shownode.node_type.img_link+"\" action=\""+self.id.to_s+"\" >\n"
      shownode.values.each {|n|
        ret+=get_tree_data_xml_vl(n)
      }
      shownode.sub_systems.each {|n|
        ret+=get_tree_data_xml_ss(n)
      }
      shownode.modes.each {|n|
        ret+=get_tree_data_xml_md(n)
      }
      ret+="</folder>\n"
    else
      ret="<leaf title=\""+shownode.name+"\" code=\""+shownode.id.to_s+"\""+" img=\""+shownode.node_type.img_link+"\"  action=\""+self.id.to_s+"\" />\n"
    end
    return ret
  end
  
  def get_tree_data_xml_md(shownode)
    if (shownode.has_children?)
      ret="<folder title=\""+shownode.name+"\" code=\""+shownode.id.to_s+"\""+" img=\""+shownode.node_type.img_link+"\" >\n"
      shownode.values.each {|n|
        ret+=get_tree_data_xml_vl(n)
      }
      shownode.sub_modes.each {|n|
        ret+=get_tree_data_xml_md(n)
      }
      ret+="</folder>\n"
    else
      ret="<leaf title=\""+shownode.name+"\" code=\""+shownode.id.to_s+"\""+" img=\""+shownode.node_type.img_link+"\" />\n"
    end
    return ret
  end

  def get_tree_data_xml_vl(shownode)
    ret="<leaf title=\""+shownode.name+"\" code=\""+shownode.id.to_s+"\""+" img=\""+shownode.node_type.img_link+"\" />\n"
    return ret
  end

  def to_code_permitted?(s)
    false
  end

end