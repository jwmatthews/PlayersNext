
Ember.TEMPLATES['link/index'] = Ember.Handlebars.compile('' +
    '{{#each content}}' +
        '<br />{{#linkTo "link.detail" this}}{{title}}{{/linkTo}}' +
        '<br />{{description}}' +
        '<br />{{tags}}' +
        '<hr/>' +
    '{{/each}}'
);

Ember.TEMPLATES['link/detail'] = Ember.Handlebars.compile('' +
    '<div>{{url}}</div>' +
    '<br />{{title}}' + 
    '<br />{{description}}' + 
    '<br />{{tags}}' + 
    '<br />{{#linkTo "link.index"}}&lt; back{{/linkTo}}'
);
