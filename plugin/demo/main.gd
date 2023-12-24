extends Node2D

var _plugin_name = "GooglePlayInAppReview"
var _google_play_in_app_review_plugin;

func _ready():
	if Engine.has_singleton(_plugin_name):
		_google_play_in_app_review_plugin = Engine.get_singleton(_plugin_name)
	else:
		printerr("Couldn't find plugin " + _plugin_name)

func _on_Button_pressed():
	if _google_play_in_app_review_plugin:
		_google_play_in_app_review_plugin.helloWorld()
