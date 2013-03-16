var PN = Ember.Application.create({
    log: function(message) {
        if (window.console) console.log(message);
    },
    LOG_TRANSITIONS: true
});

PN.store = DS.Store.create({
    adapter:  DS.RESTAdapter.create(),
    revision: 12
});

PN.store.adapter.reopen({
  namespace: 'api'
});
