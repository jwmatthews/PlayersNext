PN.ApplicationController = Ember.Controller.extend({});

PN.LinkIndexController = Ember.ArrayController.extend({
	isLoading: false,
	isEditing: false,
	url: "",
	title: "",
	description: "",
	thumnails: [],
	selected_thumbnail_index: 0,
	thumb_url: "",
	thumb_width: "",
	thumb_height: "",
	thumb_size: "",
	tags: [],
  	clearLinkSubmission: function() {
  		this.set('url', "");
  		this.set('title', "");
  		this.set('description', "");
  		this.set('tags', []);
  		this.set('thumb_url', "");
  		this.set('thumb_width', "");
  		this.set('thumb_height', "");
  		this.set('thumb_size', "");
  		//this.$('.myTag').remove();
  	},
  	beginLinkSubmit: function() {
  		this.startEditing();
  		this.set('isLoading', true);
  		var contr = this;
  		$.getJSON("/api/links/metadata/" + escape(this.get('url'))).then(function(data) {
  			console.log("Looked up URL: <" + contr.get('url') + "> found data: " + JSON.stringify(data));
  			contr.set('title', data.title);
  			contr.set('description', data.description);
  			contr.set('thumbnails', data.thumbnails);
  			contr.updateThumbnail();
  			contr.set('isLoading', false);
  		}).fail(function() {
  			contr.set('isLoading', false);
  		});
  	},
  	submitLink: function() {
  		if (this.formIsValid()) {
	     	var link = this.buildLinkFromInputs();
	     	//TODO:  Need to take care of 'keywords'/'tags'
	      	this.get('store').commit();
	      	this.clearLinkSubmission();
	    } else {
	    	console.log("Invalid data");
	    	this.clearLinkSubmission();
	    }
  		this.clearLinkSubmission();
  		this.doneEditing();
  	},
  	cancelEditing: function() {
  		this.doneEditing();
  		this.clearLinkSubmission();
  	},
  	startEditing: function() {
  		this.set('isEditing', true);
  	},
  	doneEditing: function() {
  		this.set('isEditing', false);
  	},
  	updateThumbnail: function() {
  		var thumb = this.thumbnails[this.selected_thumbnail_index]
  		if (thumb) {
  			this.set('thumb_url', thumb.url);
  			this.set('thumb_width', thumb.width);
  			this.set('thumb_height', thumb.height);
  			this.set('thumb_size', thumb.size);
  		} else {
  			this.set('thumb_url', "");
  			this.set('thumb_width', "");
  			this.set('thumb_height', "");
  			this.set('thumb_size', "");
  		}
  	},
  	prevThumbnail: function() {
  		if (this.thumbnails) {
  			if (this.selected_thumbnail_index == 0) {
  				this.set('selected_thumbnail_index',  this.thumbnails.length - 1);
  			} else {
  				this.set('selected_thumbnail_index', this.selected_thumbnail_index - 1);
  			}
  			this.updateThumbnail();
  		}
  	},
  	nextThumbnail: function() {
  		if (this.thumbnails) {
  			if (this.selected_thumbnail_index == this.thumbnails.length - 1) {
  				this.set('selected_thumbnail_index', 0);
  			} else {
  				this.set('selected_thumbnail_index',  this.selected_thumbnail_index + 1);
  			}
  			this.updateThumbnail();
  		}
  	},
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
	    if (this.tags != undefined) {
	    	this.set('tags', completed_tags + this.tags.trim());
	    }
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
	buildLinkFromInputs: function() {
	    var url = this.get('url');
	    var title = this.get('title');
	    var description = this.get('description');
	    var tags = this.get('tags');
	    var thumbnail = {}
	    thumbnail.url = this.get('thumb_url');
	    thumbnail.width = this.get('thumb_width');
	    thumbnail.height = this.get('thumb_height');
	    thumbnail.size = this.get('thumb_size');
	    return PN.Link.createRecord(
	    	{ 	url: url,
	      		title: title,
	      		description: description,
	      		tags: tags,
	      		thumbnail: thumbnail
	    });
 	},
 	formIsValid: function() {
		var url = this.get('url');
		var title = this.get('title');
		var description = this.get('description');
		var tags = this.get('tags');

		if (url === undefined || url.trim() === "") {
	  		return false;
		}
		return true;
  	}
});