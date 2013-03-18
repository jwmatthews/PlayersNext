PN.ApplicationView = Ember.View.extend({
    elementId: 'mainArea'
});

PN.LinkIndexView = Ember.View.extend({
    elementId: 'linksArea',
    addLink: function(event) {
	    if (this.formIsValid()) {
	      var link = this.buildLinkFromInputs(event);
	      this.get('controller').addLink(link);
	      this.resetForm();
	    } else {
	    	alert("Invalid data");
	    	this.resetForm();
	    }
  	},
  	buildLinkFromInputs: function(session) {
	    var url = this.get('url');
	    var title = this.get('title');
	    var description = this.get('description');
	    var tags = this.get('tags');

	    return PN.Link.createRecord(
	    	{ 	url: url,
	      		title: title,
	      		description: description,
	      		tags: tags
	    });
 	},
 	formIsValid: function() {
		var url = this.get('url');
		var title = this.get('title');
		var description = this.get('description');
		var tags = this.get('tags');

		if (url === undefined || title === undefined || url.trim() === "" || title.trim() === "") {
	  		return false;
		}
		return true;
  	},
  	resetForm: function() {
    	this.set('url', '');
    	this.set('title', '');
    	this.set('description', '');
    	this.set('tags', '');

 	}
});

