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
    } else if (Em.typeOf(value) === 'string') {
      return (value.split(','));
    } else {
      return [];
    }
  },
  deserialize: function(value) {
    return value;
  }
});

DS.RESTAdapter.registerTransform('dict', {
	serialize: function(value) {
		return value
	},
	deserialize: function(value) {
		return value
	}
});

PN.TagField = Ember.TextField.extend({
  classNames: ['linksTagManager'],
  attributeBindings: ['name'],
  didInsertElement: function() {
    this.$().tagsManager({
      preventSubmitOnEnter: true,
      typeahead: true,
      typeaheadAjaxSource: '/api/tags',
      typeaheadAjaxPolling: true,
      //AjaxPush: '/api/tags',
      blinkBGColor_1: '#FFFF9C',
      blinkBGColor_2: '#CDE69C',
      hiddenTagListName: 'tags'
    });
  }
});