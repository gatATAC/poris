// Place your application-specific JavaScript functions and classes here
// This file is automatically included by javascript_include_tag :defaults

// This function must be implemented bc it is called by DHTML Treeview every time
// you click on a leaf


function selectLeaf(title, code) {
    //location.href="/nodes/"+code;
    parent.location.href = "/nodes/"+code;
//document.getElementsByName("basefrm")[0].contentWindow.location.href="/nodes/"+code;
}
// This function is must be implemented if you use the "code" attribute into folder elements.
// It is called every time you click on a folder
function selectFolder(folderCode) {
    parent.location.href="/nodes/"+folderCode;
}
function selectAction(gen_id,code) {
    //location.href="/nodes/"+code;
    window.open("/nodes/"+code);
//parent.frames['basefrm'].location.href = gen_id+"?node="+code;
//document.getElementsByName("basefrm")[0].contentWindow.location.href="/nodes/"+code;
}

function setIframeHeight(id, h) {
    if ( document.getElementById ) {
        var theIframe = document.getElementById(id);
        if (theIframe) {
            theIframe.style.height = (document.height*h).toString()+"px";
        }
    }
}

window.onload = function() { 
    setIframeHeight('treeifrm', 800);
}
window.onresize = function() { 
    setIframeHeight('treeifrm', 800);
}

var hoboParts = {};var pluralisations = {
    story_status: 'story_statuses',
    nodes_edges: 'nodes_edges',
    task_status: 'task_statuses',
    task_assignment_status: 'task_assignment_statuses',
    story: 'stories'
}; urlBase = ''; hoboPagePath = 'sub_systems/[tag-page]'; formAuthToken = {
    name: 'authenticity_token',
    value: '4d090ebe0dc331c3f0c19c531b902af48abb004a'
}


function loadSubNode2(node,id,rubyId){
    //document.write("nodo id !"+id+" es nodo "+node.toString());
    Hobo.ajaxRequest(window.location.href, ['node3'],
//    {params: ["node="+rubyId+"&expand=true"],method: 'get', spinnerNextTo: this, message: ""} )
    {params: ["node=5&expand=true"],method: 'get', spinnerNextTo: this, message: ""} )
}

function loadSubNode(node,id,rubyId) {
    // Do nothing
}
