PN.Link = DS.Model.extend({
    id: DS.attr('string'),
    url: DS.attr('string'),
    title: DS.attr('string'),
    description: DS.attr('string'),
    postFilename: DS.attr('string'),
});

PN.Tag = DS.Model.extend({
    id: DS.attr('string'),
    value: DS.attr('string'),
    refs: DS.attr('int'),

})