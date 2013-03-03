//
// Register tagManager to handle ajax tags for links
//
$(document).ready(function() {
    if ( $(".linksTagManager")[0] ) {
        // Ensure a tagManager exists in DOM
        $(".linksTagManager").tagsManager({
            preventSubmitOnEnter: true,
            typeahead: true,
            typeaheadAjaxSource: '/api/tags',
            typeaheadAjaxPolling: true,
            //AjaxPush: '/api/tags',
            blinkBGColor_1: '#FFFF9C',
            blinkBGColor_2: '#CDE69C',
            hiddenTagListName: 'tags'
        });
    }
});
