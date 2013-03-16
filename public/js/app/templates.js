Ember.TEMPLATES['link/index'] = Ember.Handlebars.compile('' +
    '{{view Ember.TextField placeholder="url" valueBinding="view.url"}}' +
    '{{view Ember.TextField placeholder="title" valueBinding="view.title"}}' +
    '{{view Ember.TextField placeholder="description" valueBinding="view.description"}}' +
    '{{view Ember.TextField placeholder="tags" valueBinding="view.tags"}}' +
    '<a {{action addLink model target="view" href=true}}>Submit</a>' +

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