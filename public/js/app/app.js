var PN = Ember.Application.create({
    log: function(message) {
        if (window.console) console.log(message);
    },
    LOG_TRANSITIONS: true
});

PN.Store = DS.Store.extend({
    adapter:  DS.RESTAdapter.extend({
    	namespace: 'api'
    }),
    revision: 12
});

DS.RESTAdapter.registerTransform('array', {
  serialize: function(value) {
    if (Em.typeOf(value) === 'array') {
      return value;
    } else {
      return [];
    }
  },
  deserialize: function(value) {
    return value;
  }
});