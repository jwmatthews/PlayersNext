PN.ApplicationController = Ember.Controller.extend({});

PN.LinkIndexController = Ember.ArrayController.extend({
	addLink: function(link) {
		// Need to update REST API to accept way ember passes in data...or need to implement a custom adapter in JS to match our REST API
    	this.get('store').commit();
  }
});