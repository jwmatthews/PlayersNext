var PN = Ember.Application.create({
    log: function(message) {
        if (window.console) console.log(message);
    },
    LOG_TRANSITIONS: true
});

PN.Store = DS.Store.extend({
    adapter:  DS.RESTAdapter.create({
    	namespace: 'api'
    }),
    revision: 12
});
