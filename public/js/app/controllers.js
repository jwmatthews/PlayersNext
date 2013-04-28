PN.ApplicationController = Ember.Controller.extend({});

PN.LinkIndexController = Ember.ArrayController.extend({
	//
	// States
	//
	isLoading: false, // set when we are waiting for link metadata to be returned from backend
	isEditing: false, // set this when we are editing fields prior to submission
	//
	// Values representing a link a user is in the process of editing, prior to submission
	//
	url: "",
	title: "",
	description: "",
	thumbnails: [],
	selected_thumbnail_index: 0,
	thumb_url: "",
	thumb_width: 0,
	thumb_height: 0,
	thumb_size: 0,
  	clearLinkSubmission: function() {
  		this.set('url', "");
  		this.set('title', "");
  		this.set('description', "");
  		this.set('thumbnails', []);
  		this.set('selected_thumbnail_index', 0);
  		this.set('thumb_url', "");
  		this.set('thumb_width', "");
  		this.set('thumb_height', "");
  		this.set('thumb_size', "");
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
  			this.set('thumb_width', 0);
  			this.set('thumb_height', 0);
  			this.set('thumb_size', 0);
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
	buildLinkFromInputs: function() {
	    var url = this.get('url');
	    var title = this.get('title');
	    var description = this.get('description');
	    //
	    //  tagManager stores the entered 'tags' in a hidden <input> field
	    //  value may be overidden by tagManager config with attribute: 'hiddenTagListName'
  		//
  		var tags = [];
	    var tag_data = $('input[name="link_submit_tags"]').val();
  		if (tag_data) {
  			var temp_tags = tag_data.split(",");
			for (var i = 0; i < temp_tags.length; i++) {
				tags[i] = temp_tags[i].trim();  
			}
	    }
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
 		// TODO:
 		//   We need to display errors back to user so they can correct them
		var url = this.get('url');
		var title = this.get('title');
		var description = this.get('description');
		if (url === undefined || url.trim() === "") {
	  		return false;
		}

		return true;
  	}
});