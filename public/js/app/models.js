PN.Link = DS.Model.extend({
    url: DS.attr('string'),
    title: DS.attr('string'),
    description: DS.attr('string'),
    tags: DS.attr('array'),
    thumbnail: DS.attr('dict'),
});

