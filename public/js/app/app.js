var PN = Ember.Application.createWithMixins({
    log: function(message) {
        if (window.console) console.log(message);
    }
});

PN.store = DS.Store.createWithMixins({
    adapter:  DS.RESTAdapter.create(),
    revision: 12
});

DS.RESTAdapter.reopen({
  namespace: 'api'
});
