PN.ApplicationView = Ember.View.extend({
    elementId: 'mainArea'
});

PN.LinkIndexView = Ember.View.extend({
  elementId: 'linksArea',
  addLink: function(event) {
    // =====================================================================
    // this is really hacky!
    // there has to be a better/cleaner way to get the autocompleted tag and 
    // append the information to this.tag
  	var completed_tags = '';
  	this.$('.myTag').each(function() {
  		completed_tags += $(this).children('span').text().trim() + ',';
  	});
    
    // TODO: Should really take care of the case if this.tags is empty
    this.set('tags', completed_tags + this.tags.trim());
    // end hacky shit
    // =====================================================================
    if (this.formIsValid()) {
      var link = this.buildLinkFromInputs(event);
      this.get('controller').addLink(link);
      this.resetForm();
    } else {
    	console.log("Invalid data");
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
    this.$('.myTag').remove();
 	}
});

PN.LinkDetailView = Ember.View.extend({
  elementId: 'linkDetail'
  /** Need to add the below code to be run after the template has been drawn
  $('linkDetail').embedly({key: "1f920f0a1366447ca03290b7b96670cb"});
  **/
});
