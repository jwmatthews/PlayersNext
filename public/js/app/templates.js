Ember.TEMPLATES['application'] = Ember.Handlebars.compile('' +
    '<div class="masthead">' +
        '<h3 class="muted">PlayersNext</h3>' +
        '<div class="navbar">' +
            '<div class="navbar-inner">' +
                '<div class="container">' +
                    '<ul class="nav">' +
                      '<li>{{#linkTo "landing"}}Home{{/linkTo}}</li>' +
                      '<li>{{#linkTo "link.index"}}Articles{{/linkTo}}</li>' +
                      '<li>{{#linkTo "link.index"}}Guidance{{/linkTo}}</li>' +
                      '<li>{{#linkTo "link.index"}}Sports Gear{{/linkTo}}</li>' +
                      '<li>{{#linkTo "link.index"}}Find Coaches/Programs{{/linkTo}}</li>' +
                      '<li>{{#linkTo "link.index"}}Community{{/linkTo}}</li>' +
                    '</ul>' +
                '</div>' +
            '</div>' +
        '</div><!-- /.navbar -->' +
      '</div>' +
      '<div class="container">{{outlet}}</div>' 
);


Ember.TEMPLATES['landing'] = Ember.Handlebars.compile('' +
    '<!-- Jumbotron -->' +
    '<div class="jumbotron">' +
        '<h1>Marketing Stuff!</h1>' +
        '<p class="lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>' +
        '<a class="btn btn-large btn-success" href="#">Get started today</a>' +
    '</div>' +

    '<hr>' +
    '<!-- Example row of columns -->' +
    '<div class="row-fluid">' +
        '<div class="span4">' +
            '<h2>Discover how to Play</h2>' +
            '<p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>' +
            '<p><a class="btn" href="#">View details &raquo;</a></p>' +
        '</div>' +
        '<div class="span4">' +
            '<h2>Learn to Compete</h2>' +
            '<p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>' +
            '<p><a class="btn" href="#">View details &raquo;</a></p>' +
        '</div>' +
        '<div class="span4">' +
            '<h2>Focus on Winning</h2>' +
            '<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa.</p>' +
            '<p><a class="btn" href="#">View details &raquo;</a></p>' +
        '</div>' +
    '</div>'
);

Ember.TEMPLATES['link/_submitEdit'] = Ember.Handlebars.compile('' +
    '{{#if isLoading}}' +
        '<p>Please wait while we attempt to lookup link information...' +
        '<img src="/assets/images/ajax-loader.gif"/>' +
    '{{else}}' +
        '<div class="row-fluid">' +
            '<div class="span12">' +
                '<button id="cancelEdit" class="pull-right btn-inverse" {{action cancelEditing}}>X</button>' +
            '</div>' +
        '</div>' +
        // Thumbnail
        '<div class="row-fluid">' +
            '<div class="span12">' +
                '<img {{bindAttr src="thumb_url"}} {{bindAttr width=thumb_width}} {{bindAttr height=thumb_height}}/>' +
            '</div>' +
        '</div>' +
        '<div class="row-fluid">' +
            '<div class="span2">' +
                '<p>{{ selected_thumbnail_index }}/{{ thumbnails.length }}</p>' +
            '</div>' +
        '</div>' +
        // Render select URL for thumnail
        '<div class="row-fluid">' +
            '<div class="span1 pull-left">' +
                '<button class="btn btn-mini" {{action prevThumbnail}}>\<</button>' +
            '</div>' +      
            '{{view Ember.TextField class="span10" placeholder="Paste URL for thumbnail" valueBinding="thumb_url"}}' +
            '<div class="span1 pull-right">' +
                '<button class="btn btn-mini" {{action nextThumbnail}}>\></button>' +
            '</div>' +
        '</div>' +
        // End Thumbnail
        '<div class="row-fluid">' +
            '<div class="span12">' +
                '{{view Ember.TextField class="span9" placeholder="Paste URL here to share..." valueBinding="url"}}' +
            '</div>' +
        '</div>' +
        '<div class="row-fluid">' +
            '<div class="span12">' +
                '{{view Ember.TextField placeholder="Enter title here..." valueBinding="title"}}' +
            '</div>' +
        '</div>' +
        '<div class="row-fluid">' +
            '<div class="span12">' +
                '{{view Ember.TextArea placeholder="Enter description here..." valueBinding="description"}}' +
            '</div>' +
        '</div>' +
        '<div class="row-fluid">' +
            '<div class="span12">' +
                '{{view PN.TagField placeholder="Enter keywords here..." valueBinding="view.tags" name="tagsjax"}}' +
            '</div>' +
        '</div>' +
        '<button class="btn btn-primary" {{action submitLink}}>Submit</button>' +
    '{{/if}}'
);

Ember.TEMPLATES['link/index'] = Ember.Handlebars.compile('' +
    '<div class="nav well span6">' +
        '{{#if isEditing}}'+
            '{{partial "link/submitEdit"}}' +
        '{{else}}' +
            '<p>Please paste a URL below to share a link....</p>'+
            '{{view Ember.TextField placeholder="Paste URL here to share..." valueBinding="url" action="beginLinkSubmit"}}'+
        '{{/if}}' +
    '</div>' +
    '<div class="row-fluid">' +
        '<div class="span12">' +
            '<br />'+
        '</div>' +
    '</div>' +
    '{{#each content}}' +
        '<div class="row-fluid">' +
            '<div class="span12">' +
                '<hr /> '+
            '</div>' +
        '</div>' +
        '<div class="row-fluid">' +
            '<div class="span12">' +
                'Title: {{#linkTo "link.detail" this}}{{title}}{{/linkTo}}' +
               '</div>' +
            '</div>' +    
        '<div class="row-fluid">' +
            '<div class="span12">' +        
                'URL: <a {{bindAttr href="url" }}>{{url}} </a>' +
            '</div>' +
        '</div>' +
        '<div class="row-fluid">' +
            '<div class="span12">' +
                'Description: {{description}}' +
           '</div>' +
        '</div>' +
        '<div class="row-fluid">' +
            '<div class="span12">' +
                'Thumbnail: url={{thumbnail_url}}, thumbnail_width={{thumbnail_width}}, thumbnail_height={{thumbnail_height}}, thumbnail_size={{thumbnail_size}}' +
            '</div>' +
        '</div>' +
        '<div class="row-fluid">' +
            '<div class="span12">' +
                'Tags: {{tags}}' +
            '</div>' +
        '</div>' +
    '{{/each}}'
);

Ember.TEMPLATES['link/detail'] = Ember.Handlebars.compile('' +
    '<div class="row-fluid">' +
        '<div class="span12">' +
            '<a {{bindAttr href="url" }}>{{url}} </a>' +
            '</div>' +
        '</div>' +
    '<div class="row-fluid">' +
        '<div class="span12">' +
            'Title: {{title}}' +
        '</div>' +
    '</div>' +
    '<div class="row-fluid">' +
        '<div class="span12">' +
            'Description: {{description}}' +
        '</div>' +
    '</div>' +
    '<div class="row-fluid">' +
        '<div class="span12">' +
            'Tags: {{tags}}' +
        '</div>' +
    '</div>' +
    '<div class="row-fluid">' +
        '<div class="span12">' +
            '{{#linkTo "link.index"}}&lt; back{{/linkTo}}' +
        '</div>' +
    '</div>'
);