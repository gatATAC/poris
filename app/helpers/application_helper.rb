# Methods added to this helper will be available to all templates in the application.
module ApplicationHelper

  def get_tree_data(root,shownode,collapsed,path)
    ret=""
    if root
      path+=root.id.to_s+"."
      if not collapsed then
        root.style = 'display:inline'
        display_expanded = 'inline'
        display_collapsed = 'none'
        ret += "<div class='inner_tree_element' id='#{path}_seltree_div'>"
        if root.has_children?
          ret += "<img id='#{path}expanded' src='/images/expanded.gif'  style='display:#{display_expanded}; cursor:pointer;'  />  "
          ret += "<img id='#{path}collapsed' src='/images/collapsed.gif' style='display:#{display_collapsed}; cursor:pointer;'/>  "
        else
          ret += "<img id='#{path}expanded' src='/images/fixed.gif'  style='display:#{display_expanded}; cursor:pointer;'  />  "
          ret += "<img id='#{path}collapsed' src='/images/fixed.gif'  style='display:#{display_collapsed}; cursor:pointer;'  />  "
        end
      else
        if shownode==root or shownode.pretree.include?(root)
          root.style = 'display:inline'
          display_expanded = 'inline'
          display_collapsed = 'none'
        else
          root.style = 'display:none'
          display_expanded = 'none'
          display_collapsed = 'inline'
        end
        ret += "<div class='inner_tree_element' id='#{path}_tree_div'>"
        if root.has_children?
          ret += "<img id='#{path}expanded' src='/images/expanded.gif' onclick='javascript: return toggleMyTree(\"#{path}\"); ' style='display:#{display_expanded}; cursor:pointer;'  />  "
          ret += "<img id='#{path}collapsed' src='/images/collapsed.gif' onclick='javascript: return toggleMyTree(\"#{path}\"); ' style='display:#{display_collapsed}; cursor:pointer;'   />  "
        else
          ret += "<img id='#{path}expanded' src='/images/fixed.gif'  style='display:#{display_expanded}; cursor:pointer;'  />  "
          ret += "<img id='#{path}collapsed' src='/images/fixed.gif'  style='display:#{display_collapsed}; cursor:pointer;'  />  "
        end
      end
      ret += " <img src='"
      if root.node_type then
        ret +=root.node_type.img_link
      else
        ret +="/images/drag.gif"
      end
      if not collapsed then
        ret +="' style='cursor:move' id='#{path}_seldrag_image' align='absmiddle' class='seldrag_image' /> "
      else
        ret +="' style='cursor:move' id='#{path}_drag_image' align='absmiddle' class='drag_image' /> "
      end
      ret += "<span id='#{path}_tree_item'>"
      ret += yield root
      ret += "</span>"
      ret += "<span id='#{path}children' style='#{root.style}' >"
      childs=root.destinations
    else
      childs=Node.roots
    end
    childs.each { |n|
      if n!=root
        ret+=get_tree_data(n,shownode,collapsed,path) {|n| yield n}
      end
    }
    ret += "</span>"
    ret +="</div>"
    return ret
  end

  def compute_public_path_2(source, dir, ext = nil, include_host = true)
    has_request = @controller.respond_to?(:request)

    source_ext = File.extname(source)[1..-1]
    if ext && (source_ext.blank? || (ext != source_ext && File.exist?(File.join(ASSETS_DIR, dir, "#{source}.#{ext}"))))
      source += ".#{ext}"
    end

    unless source =~ %r{^[-a-z]+://}
      source = "/#{dir}/#{source}" unless source[0] == ?/

      source = rewrite_asset_path(source)

      if has_request && include_host
        unless source =~ %r{^#{ActionController::Base.relative_url_root}/}
          source = "#{ActionController::Base.relative_url_root}#{source}"
        end
      end
    end

    if include_host && source !~ %r{^[-a-z]+://}
      host = compute_asset_host(source)

      if has_request && !host.blank? && host !~ %r{^[-a-z]+://}
        host = "#{@controller.request.protocol}#{host}"
      end

      "#{host}#{source}"
    else
      source
    end
  end

end
