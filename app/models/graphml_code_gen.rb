class GraphmlCodeGen < CodeGen

  def to_code(init,s,doneelems)
    #allowed=s.view_permitted?
    allowed=true
    if (allowed)
      ret="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>
      <graphml xmlns=\"http://graphml.graphdrawing.org/xmlns/graphml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:y=\"http://www.yworks.com/xml/graphml\" xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns/graphml http://www.yworks.com/xml/schema/graphml/1.0/ygraphml.xsd\">
      <key attr.name=\"url\" attr.type=\"string\" for=\"node\" id=\"d0\"/>
      <key attr.name=\"description\" attr.type=\"string\" for=\"node\" id=\"d1\"/>
      <key for=\"node\" id=\"d2\" yfiles.type=\"nodegraphics\"/>
      <key attr.name=\"url\" attr.type=\"string\" for=\"edge\" id=\"d3\"/>
      <key attr.name=\"description\" attr.type=\"string\" for=\"edge\" id=\"d4\"/>
      <key for=\"edge\" id=\"d5\" yfiles.type=\"edgegraphics\"/>
      <key for=\"graphml\" id=\"d6\" yfiles.type=\"resources\"/>";
      ret+=to_code_int(s);
      ret+="<data key=\"d4\">
        <y:Resources/>
      </data>
      </graphml>"
      return ret,[doneelems,s.subtree]
    else
      "Not allowed operation"
    end
  end

  def to_code_embedded(s)
    return to_code_int(s)
  end
  
  def to_code_int(s)
    #allowed=s.view_permitted?
    allowed=true
    if allowed
      ret="<graph edgedefault=\"directed\" id=\"G\" parse.edges=\"0\" parse.nodes=\"1\" parse.order=\"free\">"
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
          row=0
          column=0
          ret2,row,column=get_tree_data_xml_ss(s,row,column){|n|
            #link_to(n.name,{:controller=>'nodes', :action=>'show', :id=>n.id}, :target => "main") }
            "<a href='/nodes/"+n.id.to_s+"' target='main'>"+n.name+"</a>"
          }
          ret+=ret2
        end
      end
      ret+="</graph>"
      return ret
    else
      "Not allowed operation 2"
    end
  end

  def get_tree_data_xml_lb(shownode)
    if (shownode.has_children?)
      ret="<folder title=\""+shownode.to_s_scope_kind(self)+"\" code=\""+shownode.id.to_s+"\""+" img=\""+shownode.node_type.img_link+"\" action=\""+self.id.to_s+"\" >"
      shownode.sub_libs.each {|n|
        ret+=get_tree_data_xml_lb(n)
      }
      shownode.systems.each {|n|
        ret+=get_tree_data_xml_ss(n)
      }
      ret+="</folder>"
    else
      ret="<leaf title=\""+shownode.to_s_scope_kind(self)+"\" code=\""+shownode.id.to_s+"\""+" img=\""+shownode.node_type.img_link+"\" action=\""+self.id.to_s+"\" />"
    end
    return ret
  end

  def get_tree_data_xml_ss(shownode,row,column)
    ret="<node id=\"n"+shownode.id.to_s+"\" yfiles.foldertype=\"group\">
      <graph edgedefault=\"directed\" id=\"n"+shownode.id.to_s+":\">"
    value_row=row+1
    mode_row=row+1
    ss_row=row
    max_column=1
    if (shownode.values.size>0)
      value_column_width=1
    else
      value_column_width=0
    end
    if (shownode.modes.size>0)
      mode_column_width=1
    else
      mode_column_width=0
    end
    initial=true
    antn=nil
    shownode.values.each {|n|
      ret2,value_row,value_column=get_tree_data_xml_vl(n,value_row,column+mode_column_width+1)
      ret+=ret2
      value_column_width=1
      if (initial)
        initial=false
      else
        if (antn)
          ret+="<edge id=\"ve"+antn.id.to_s+"ve"+n.id.to_s+"\" source=\"n"+antn.id.to_s+"\" target=\"n"+n.id.to_s+"\">
      <data key=\"d3\"/>
      <data key=\"d4\"/>
      <data key=\"d5\">
        <y:PolyLineEdge>
          <y:Path sx=\"0.0\" sy=\"0.0\" tx=\"0.0\" ty=\"0.0\"/>
          <y:LineStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>
          <y:Arrows source=\"none\" target=\"standard\"/>
          <y:BendStyle smoothed=\"false\"/>
        </y:PolyLineEdge>
      </data>
    </edge>"
        end
      end
      antn=n
    }
    shownode.sub_systems.each {|n|
      ret2,ss_row,ss_column=get_tree_data_xml_ss(n,ss_row,column+mode_column_width+1+value_column_width)
      ret+=ret2
      if (ss_column>max_column)
        max_column=ss_column
      end
    }
    shownode.modes.each {|n|
      ret2,mode_row,mode_column=get_tree_data_xml_md(n,mode_row,column)
      ret+=ret2
      mode_column_width=1
    }
    if (ss_row>value_row)
      max_row=ss_row
    else
      max_row=value_row
    end
    if (mode_row>max_row)
      max_row=mode_row
    end
    row_size=max_row-row
    column_size=value_column_width+mode_column_width+max_column+1
    ret+="</graph>
      <data key=\"d0\">http://"+shownode.project.hostnameport+"/nodes/"+shownode.id.to_s+"</data>
      <data key=\"d1\">groupNode0</data>
      <data key=\"d2\">
        <y:ProxyAutoBoundsNode>
          <y:Realizers active=\"0\">
            <y:GroupNode>
              <y:Geometry height=\""+(row_size*60).to_s+"\" width=\""+(column_size*80).to_s+"\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#F5F5F5\" transparent=\"false\"/>
              <y:BorderStyle color=\"#000000\" type=\"dashed\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"right\" autoSizePolicy=\"node_width\" backgroundColor=\"#EBEBEB\" fontFamily=\"Dialog\" fontSize=\"15\" fontStyle=\"plain\" hasLineColor=\"false\" height=\"22.37646484375\" modelName=\"internal\" modelPosition=\"t\" textColor=\"#000000\" visible=\"true\" width=\"135.0\" x=\"0.0\" y=\"0.0\">"+shownode.to_s_scope_kind(self)+"</y:NodeLabel>
              <y:Shape type=\"roundrectangle\"/>
              <y:State closed=\"false\" innerGraphDisplayEnabled=\"false\"/>
              <y:Insets bottom=\"15\" left=\"15\" right=\"15\" top=\"15\"/>
              <y:BorderInsets bottom=\"0\" left=\"0\" right=\"0\" top=\"0\"/>
            </y:GroupNode>
            <y:GroupNode>
              <y:Geometry height=\""+(row_size*60).to_s+"\" width=\""+(column_size*80).to_s+"\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#CAECFF84\" transparent=\"false\"/>
              <y:BorderStyle color=\"#666699\" type=\"dotted\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"right\" autoSizePolicy=\"node_width\" backgroundColor=\"#99CCFF\" fontFamily=\"Dialog\" fontSize=\"15\" fontStyle=\"plain\" hasLineColor=\"false\" height=\"22.37646484375\" modelName=\"internal\" modelPosition=\"t\" textColor=\"#000000\" visible=\"true\" width=\"100.0\" x=\"0.0\" y=\"0.0\">"+shownode.to_s_scope_kind(self)+"</y:NodeLabel>
              <y:Shape type=\"roundrectangle\"/>
              <y:State closed=\"true\" innerGraphDisplayEnabled=\"false\"/>
              <y:Insets bottom=\"15\" left=\"15\" right=\"15\" top=\"15\"/>
              <y:BorderInsets bottom=\"0\" left=\"0\" right=\"0\" top=\"0\"/>
            </y:GroupNode>
          </y:Realizers>
        </y:ProxyAutoBoundsNode>
      </data>
    </node>"
    return ret,max_row,column_size
  end

  def get_tree_data_xml_md(shownode,row,column)
    ret="<node id=\"n"+shownode.id.to_s+"\">
              <data key=\"d0\">http://"+shownode.project.hostnameport+"/nodes/"+shownode.id.to_s+"</data>
              <data key=\"d1\"/>
              <data key=\"d2\">
                <y:ShapeNode>
                  <y:Geometry height=\"30.0\" width=\"92.0\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
                <y:Fill color=\"#FFCC00\" transparent=\"false\"/>
                  <y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>
                  <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"34.69140625\" x=\"0.0\" y=\"0.0\">"+shownode.to_s_scope_kind(self)+"</y:NodeLabel>
                  <y:Shape type=\"roundrectangle\"/>
                </y:ShapeNode>
              </data>
            </node>"
    order=1
    shownode.values.each {|n|
      ret+="<edge id=\"e"+shownode.id.to_s+"e"+n.id.to_s+"\" source=\"n"+shownode.id.to_s+"\" target=\"n"+n.id.to_s+"\">
            <data key=\"d3\"/>
            <data key=\"d4\"/>
              <data key=\"d5\">
                <y:PolyLineEdge>
                  <y:Path sx=\"0.0\" sy=\"0.0\" tx=\"0.0\" ty=\"0.0\"/>
                  <y:LineStyle color=\"#FF6600\" type=\"line\" width=\"1.0\"/>"
      if (shownode.default_value==n || (!shownode.default_value && order==0) )
        ret+="<y:Arrows source=\"diamond\" target=\"plain\"/>"
      else
        ret+="<y:Arrows source=\"none\" target=\"plain\"/>"
      end
      ret+="<y:EdgeLabel alignment=\"center\" distance=\"2.0\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"six_pos\" modelPosition=\"tail\" preferredPlacement=\"anywhere\" ratio=\"0.5\" textColor=\"#FF9900\" visible=\"true\" width=\"10.673828125\" x=\"-31.1668701171875\" y=\"39.68543118744894\">"+order.to_s+"</y:EdgeLabel>
                  <y:BendStyle smoothed=\"false\"/>
                </y:PolyLineEdge>
              </data>
            </edge>"
      order+=1
    }
    order=1
    shownode.sub_modes.each {|n|
      ret+="<edge id=\"e"+shownode.id.to_s+"e"+n.id.to_s+"\" source=\"n"+shownode.id.to_s+"\" target=\"n"+n.id.to_s+"\">
            <data key=\"d3\"/>
            <data key=\"d4\"/>
              <data key=\"d5\">
                <y:PolyLineEdge>
                  <y:Path sx=\"0.0\" sy=\"0.0\" tx=\"0.0\" ty=\"0.0\"/>
                  <y:LineStyle color=\"#FF6600\" type=\"line\" width=\"1.0\"/>"
      if (shownode.default_mode==n || (!shownode.default_mode && order==0) )
        ret+="<y:Arrows source=\"diamond\" target=\"plain\"/>"
      else
        ret+="<y:Arrows source=\"none\" target=\"plain\"/>"
      end
      ret+="<y:EdgeLabel alignment=\"center\" distance=\"2.0\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"six_pos\" modelPosition=\"tail\" preferredPlacement=\"anywhere\" ratio=\"0.5\" textColor=\"#FF9900\" visible=\"true\" width=\"10.673828125\" x=\"-31.1668701171875\" y=\"39.68543118744894\">"+order.to_s+"</y:EdgeLabel>
                  <y:BendStyle smoothed=\"false\"/>
                </y:PolyLineEdge>
              </data>
            </edge>"
      order+=1
    }

    max_row=row
    return ret,max_row+1,column
  end

  def get_tree_data_xml_md_ant(shownode)
    if (shownode.has_children?)
      ret="<folder title=\""+shownode.to_s_scope_kind(self)+"\" code=\""+shownode.id.to_s+"\""+" img=\""+shownode.node_type.img_link+"\" >"
      shownode.values.each {|n|
        ret+=get_tree_data_xml_vl(n)
      }
      shownode.sub_modes.each {|n|
        ret+=get_tree_data_xml_md(n)
      }
      ret+="</folder>"
    else
      ret="<leaf title=\""+shownode.to_s_scope_kind(self)+"\" code=\""+shownode.id.to_s+"\""+" img=\""+shownode.node_type.img_link+"\" />"
    end
    return ret
  end

  def get_tree_data_xml_vl(shownode,row,column)
    if (shownode.class==ValueDoubleRange)
      ret,max_row,column=get_tree_data_xml_vl_range(shownode,row,column)
    else
      if (shownode.class==ValueDateRange)
        ret,max_row,column=get_tree_data_xml_vl_date(shownode,row,column)
      else
        if (shownode.class==ValueString)
          ret,max_row,column=get_tree_data_xml_vl_string(shownode,row,column)
        else
          if (shownode.class==ValueFilePath)
            ret,max_row,column=get_tree_data_xml_vl_file_path(shownode,row,column)
          else
            ret="<node id=\"n"+shownode.id.to_s+"\">
              <data key=\"d0\">http://"+shownode.project.hostnameport+"/nodes/"+shownode.id.to_s+"</data>
              <data key=\"d1\"/>
              <data key=\"d2\">
                <y:ShapeNode>
                  <y:Geometry height=\"30.0\" width=\"92.0\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
                  <y:Fill color=\"#99CCFF\" transparent=\"false\"/>
                  <y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>
                  <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"34.69140625\" x=\"0.0\" y=\"0.0\">"+shownode.to_s_scope_kind(self)+"</y:NodeLabel>
                  <y:Shape type=\"parallelogram\"/>
                </y:ShapeNode>
              </data>
            </node>"
          max_row=row+1
          end
        end
      end
    end
    return ret,max_row,column
  end
  def get_tree_data_xml_vl_string(shownode,row,column)
    ret="<node id=\"n"+shownode.id.to_s+"\">
              <data key=\"d0\">http://"+shownode.project.hostnameport+"/nodes/"+shownode.id.to_s+"</data>
              <data key=\"d1\"/>
              <data key=\"d2\">
                <y:ShapeNode>
                  <y:Geometry height=\"30.0\" width=\"92.0\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
                  <y:Fill color=\"#CCFFCC\" transparent=\"false\"/>
                  <y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>
                  <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"34.69140625\" x=\"0.0\" y=\"0.0\">"+shownode.to_s_scope_kind(self)+"</y:NodeLabel>
                  <y:Shape type=\"parallelogram\"/>
                </y:ShapeNode>
              </data>
            </node>"
    max_row=row+1
    return ret,max_row,column
  end

  def get_tree_data_xml_vl_file_path(shownode,row,column)
    ret="<node id=\"n"+shownode.id.to_s+"\">
              <data key=\"d0\">http://"+shownode.project.hostnameport+"/nodes/"+shownode.id.to_s+"</data>
              <data key=\"d1\"/>
              <data key=\"d2\">
                <y:ShapeNode>
                  <y:Geometry height=\"30.0\" width=\"92.0\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
                  <y:Fill color=\"#CCFFCC\" transparent=\"false\"/>
                  <y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>
                  <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"34.69140625\" x=\"0.0\" y=\"0.0\">"+shownode.to_s_scope_kind(self)+"</y:NodeLabel>
                  <y:Shape type=\"parallelogram\"/>
                </y:ShapeNode>
              </data>
            </node>"
    max_row=row+1
    return ret,max_row,column
  end


  def get_tree_data_xml_vl_range(shownode,row,column)

    ret="<node id=\"n"+shownode.id.to_s+"\" yfiles.foldertype=\"group\">
      <graph edgedefault=\"directed\" id=\"n"+shownode.id.to_s+":\">
        <node id=\"n"+shownode.id.to_s+"::n0\">
          <data key=\"d0\"/>
          <data key=\"d1\"/>
          <data key=\"d2\">
            <y:ShapeNode>
              <y:Geometry height=\"30.0\" width=\"30.0\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#FFFF00\" transparent=\"false\"/>
              <y:BorderStyle hasColor=\"false\" type=\"line\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"20.681640625\" x=\"4.6591796875\" y=\"5.6494140625\">"+shownode.rangemin.to_s+"</y:NodeLabel>
              <y:Shape type=\"ellipse\"/>
            </y:ShapeNode>
          </data>
        </node>
        <node id=\"n"+shownode.id.to_s+"::n1\">
          <data key=\"d0\"/>
          <data key=\"d1\"/>
          <data key=\"d2\">
            <y:ShapeNode>
              <y:Geometry height=\"30.0\" width=\"30.0\" x=\""+((column+1)*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#FFFF00\" transparent=\"false\"/>
              <y:BorderStyle hasColor=\"false\" type=\"line\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"30.6953125\" x=\"-0.34765625\" y=\"5.6494140625\">"+shownode.rangemax.to_s+"</y:NodeLabel>
              <y:Shape type=\"ellipse\"/>
            </y:ShapeNode>
          </data>
        </node>
        <node id=\"n"+shownode.id.to_s+"::n2\">
          <data key=\"d0\"/>
          <data key=\"d1\"/>
          <data key=\"d2\">
            <y:ShapeNode>
              <y:Geometry height=\"30.0\" width=\"30.0\" x=\""+((column+0.5)*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#FFFF00\" transparent=\"false\"/>
              <y:BorderStyle hasColor=\"false\" type=\"line\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"34.029296875\" x=\"-2.0146484375\" y=\"5.6494140625\">"+shownode.default_float.to_s+"</y:NodeLabel>
              <y:Shape type=\"ellipse\"/>
            </y:ShapeNode>
          </data>
        </node>
      </graph>
      <data key=\"d0\"/>
      <data key=\"d1\">groupNode1</data>
      <data key=\"d2\">
        <y:ProxyAutoBoundsNode>
          <y:Realizers active=\"0\">
            <y:GroupNode>
              <y:Geometry height=\"30.0\" width=\"92.0\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#CCFFCC\" transparent=\"false\"/>
              <y:BorderStyle color=\"#666699\" type=\"dotted\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"right\" autoSizePolicy=\"node_width\" fontFamily=\"Dialog\" fontSize=\"15\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"22.37646484375\" modelName=\"internal\" modelPosition=\"t\" textColor=\"#000000\" visible=\"true\" width=\"164.34765625\" x=\"0.0\" y=\"0.0\">"+shownode.to_s_scope_kind(self)+"</y:NodeLabel>
              <y:Shape type=\"parallelogram\"/>
              <y:State closed=\"false\" innerGraphDisplayEnabled=\"false\"/>
              <y:Insets bottom=\"15\" left=\"15\" right=\"15\" top=\"15\"/>
              <y:BorderInsets bottom=\"0\" left=\"0\" right=\"0\" top=\"0\"/>
            </y:GroupNode>
            <y:GroupNode>
              <y:Geometry height=\"30.0\" width=\"92.0\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#CAECFF84\" transparent=\"false\"/>
              <y:BorderStyle color=\"#666699\" type=\"dotted\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"right\" autoSizePolicy=\"node_width\" backgroundColor=\"#99CCFF\" fontFamily=\"Dialog\" fontSize=\"15\" fontStyle=\"plain\" hasLineColor=\"false\" height=\"22.37646484375\" modelName=\"internal\" modelPosition=\"t\" textColor=\"#000000\" visible=\"true\" width=\"129.859375\" x=\"-14.9296875\" y=\"0.0\">"+shownode.to_s_scope_kind(self)+"</y:NodeLabel>
              <y:Shape type=\"parallelogram\"/>
              <y:State closed=\"true\" innerGraphDisplayEnabled=\"false\"/>
              <y:Insets bottom=\"15\" left=\"15\" right=\"15\" top=\"15\"/>
              <y:BorderInsets bottom=\"0\" left=\"0\" right=\"0\" top=\"0\"/>
            </y:GroupNode>
          </y:Realizers>
        </y:ProxyAutoBoundsNode>
      </data>
    </node>
    <edge id=\"ie"+shownode.id.to_s+"e0\" source=\"n"+shownode.id.to_s+"::n0\" target=\"n"+shownode.id.to_s+"::n2\">
      <data key=\"d3\"/>
      <data key=\"d4\"/>
      <data key=\"d5\">
        <y:PolyLineEdge>
          <y:Path sx=\"0.0\" sy=\"0.0\" tx=\"0.0\" ty=\"0.0\"/>
          <y:LineStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>
          <y:Arrows source=\"none\" target=\"standard\"/>
          <y:BendStyle smoothed=\"false\"/>
        </y:PolyLineEdge>
      </data>
    </edge>
    <edge id=\"ie"+shownode.id.to_s+"e1\" source=\"n"+shownode.id.to_s+"::n2\" target=\"n"+shownode.id.to_s+"::n1\">
      <data key=\"d3\"/>
      <data key=\"d4\"/>
      <data key=\"d5\">
        <y:PolyLineEdge>
          <y:Path sx=\"0.0\" sy=\"0.0\" tx=\"0.0\" ty=\"0.0\"/>
          <y:LineStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>
          <y:Arrows source=\"none\" target=\"standard\"/>
          <y:BendStyle smoothed=\"false\"/>
        </y:PolyLineEdge>
      </data>
    </edge>"
    max_row=row+1
    return ret,max_row,column
  end
  def get_tree_data_xml_vl_date(shownode,row,column)

    ret="<node id=\"n"+shownode.id.to_s+"\" yfiles.foldertype=\"group\">
      <graph edgedefault=\"directed\" id=\"n"+shownode.id.to_s+":\">
        <node id=\"n"+shownode.id.to_s+"::n0\">
          <data key=\"d0\"/>
          <data key=\"d1\"/>
          <data key=\"d2\">
            <y:ShapeNode>
              <y:Geometry height=\"30.0\" width=\"30.0\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#FFFF00\" transparent=\"false\"/>
              <y:BorderStyle hasColor=\"false\" type=\"line\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"20.681640625\" x=\"4.6591796875\" y=\"5.6494140625\">"+shownode.date_min.to_s+"</y:NodeLabel>
              <y:Shape type=\"ellipse\"/>
            </y:ShapeNode>
          </data>
        </node>
        <node id=\"n"+shownode.id.to_s+"::n1\">
          <data key=\"d0\"/>
          <data key=\"d1\"/>
          <data key=\"d2\">
            <y:ShapeNode>
              <y:Geometry height=\"30.0\" width=\"30.0\" x=\""+((column+1)*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#FFFF00\" transparent=\"false\"/>
              <y:BorderStyle hasColor=\"false\" type=\"line\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"30.6953125\" x=\"-0.34765625\" y=\"5.6494140625\">"+shownode.date_max.to_s+"</y:NodeLabel>
              <y:Shape type=\"ellipse\"/>
            </y:ShapeNode>
          </data>
        </node>
        <node id=\"n"+shownode.id.to_s+"::n2\">
          <data key=\"d0\"/>
          <data key=\"d1\"/>
          <data key=\"d2\">
            <y:ShapeNode>
              <y:Geometry height=\"30.0\" width=\"30.0\" x=\""+((column+0.5)*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#FFFF00\" transparent=\"false\"/>
              <y:BorderStyle hasColor=\"false\" type=\"line\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"34.029296875\" x=\"-2.0146484375\" y=\"5.6494140625\">"+shownode.default_date.to_s+"</y:NodeLabel>
              <y:Shape type=\"ellipse\"/>
            </y:ShapeNode>
          </data>
        </node>
      </graph>
      <data key=\"d0\"/>
      <data key=\"d1\">groupNode1</data>
      <data key=\"d2\">
        <y:ProxyAutoBoundsNode>
          <y:Realizers active=\"0\">
            <y:GroupNode>
              <y:Geometry height=\"30.0\" width=\"92.0\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#CCFFCC\" transparent=\"false\"/>
              <y:BorderStyle color=\"#666699\" type=\"dotted\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"right\" autoSizePolicy=\"node_width\" fontFamily=\"Dialog\" fontSize=\"15\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"22.37646484375\" modelName=\"internal\" modelPosition=\"t\" textColor=\"#000000\" visible=\"true\" width=\"164.34765625\" x=\"0.0\" y=\"0.0\">"+shownode.to_s_scope_kind(self)+"</y:NodeLabel>
              <y:Shape type=\"parallelogram\"/>
              <y:State closed=\"false\" innerGraphDisplayEnabled=\"false\"/>
              <y:Insets bottom=\"15\" left=\"15\" right=\"15\" top=\"15\"/>
              <y:BorderInsets bottom=\"0\" left=\"0\" right=\"0\" top=\"0\"/>
            </y:GroupNode>
            <y:GroupNode>
              <y:Geometry height=\"30.0\" width=\"92.0\" x=\""+(column*80).to_s+"\" y=\""+(row*60).to_s+"\"/>
              <y:Fill color=\"#CAECFF84\" transparent=\"false\"/>
              <y:BorderStyle color=\"#666699\" type=\"dotted\" width=\"1.0\"/>
              <y:NodeLabel alignment=\"right\" autoSizePolicy=\"node_width\" backgroundColor=\"#99CCFF\" fontFamily=\"Dialog\" fontSize=\"15\" fontStyle=\"plain\" hasLineColor=\"false\" height=\"22.37646484375\" modelName=\"internal\" modelPosition=\"t\" textColor=\"#000000\" visible=\"true\" width=\"129.859375\" x=\"-14.9296875\" y=\"0.0\">"+shownode.to_s_scope_kind(self)+"</y:NodeLabel>
              <y:Shape type=\"parallelogram\"/>
              <y:State closed=\"true\" innerGraphDisplayEnabled=\"false\"/>
              <y:Insets bottom=\"15\" left=\"15\" right=\"15\" top=\"15\"/>
              <y:BorderInsets bottom=\"0\" left=\"0\" right=\"0\" top=\"0\"/>
            </y:GroupNode>
          </y:Realizers>
        </y:ProxyAutoBoundsNode>
      </data>
    </node>
    <edge id=\"ie"+shownode.id.to_s+"e0\" source=\"n"+shownode.id.to_s+"::n0\" target=\"n"+shownode.id.to_s+"::n2\">
      <data key=\"d3\"/>
      <data key=\"d4\"/>
      <data key=\"d5\">
        <y:PolyLineEdge>
          <y:Path sx=\"0.0\" sy=\"0.0\" tx=\"0.0\" ty=\"0.0\"/>
          <y:LineStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>
          <y:Arrows source=\"none\" target=\"standard\"/>
          <y:BendStyle smoothed=\"false\"/>
        </y:PolyLineEdge>
      </data>
    </edge>
    <edge id=\"ie"+shownode.id.to_s+"e1\" source=\"n"+shownode.id.to_s+"::n2\" target=\"n"+shownode.id.to_s+"::n1\">
      <data key=\"d3\"/>
      <data key=\"d4\"/>
      <data key=\"d5\">
        <y:PolyLineEdge>
          <y:Path sx=\"0.0\" sy=\"0.0\" tx=\"0.0\" ty=\"0.0\"/>
          <y:LineStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>
          <y:Arrows source=\"none\" target=\"standard\"/>
          <y:BendStyle smoothed=\"false\"/>
        </y:PolyLineEdge>
      </data>
    </edge>"
    max_row=row+1
    return ret,max_row,column
  end

  def to_code_permitted?(s)
    false
  end

end
