Ember.TEMPLATES['application'] = Ember.Handlebars.compile('' +
    '<h1>PlayersNext</h1>' +
    '<div class="headerLinks">' +
    '{{#linkTo "link.index"}}Home{{/linkTo}}<span class="middot">&middot;' +
    '</div>' +
    '{{outlet}}'
);

Ember.TEMPLATES['link/index'] = Ember.Handlebars.compile('' +
    '{{#each content}}' +
        '<h1>{{title}}</h1>' +
        '{{description}}<br />' +
        '<br />{{#linkTo "link.detail" this}}Link Info ->{{/linkTo}}' +
        '<hr/>' +
    '{{/each}}'
);

Ember.TEMPLATES['link/detail'] = Ember.Handlebars.compile('' +
    '<div>{{url}}</div>' +
    '<br />{{#linkTo "link.index"}}&lt; back{{/linkTo}}'
);
