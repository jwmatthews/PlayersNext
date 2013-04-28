PN.ApplicationView = Ember.View.extend({
    elementId: 'mainArea',
});

PN.LinkIndexView = Ember.View.extend({
  elementId: 'linksArea',
  
});

PN.LinkDetailView = Ember.View.extend({
  elementId: 'linkDetail',
  didInsertElement: function() {
  }
});
