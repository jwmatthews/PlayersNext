PN.Link = DS.Model.extend({
    url: DS.attr('string'),
    title: DS.attr('string'),
    description: DS.attr('string'),
    tags: DS.attr('string'),
});

PN.Tag = DS.Model.extend({
    tag: DS.attr('string'),
    refs: DS.attr('int'),
})