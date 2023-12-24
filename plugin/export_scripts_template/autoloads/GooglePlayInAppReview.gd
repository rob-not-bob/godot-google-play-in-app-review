extends Node

var _plugin_name = "GooglePlayInAppReview"
var _plugin;

signal review_info_requested(was_successful: bool);
signal review_flow_complete();

func _ready():
	if Engine.has_singleton(_plugin_name):
		_plugin = Engine.get_singleton(_plugin_name)
		_connect_signals();
	else:
		printerr("Couldn't find plugin " + _plugin_name)


func _connect_signals() -> void:
	_plugin.review_info_requested.connect(func(was_successful: bool):
		self.review_info_requested.emit(was_successful);
	)
	_plugin.review_flow_complete.connect(func():
		self.review_flow_complete.emit();
	)


func requestReviewInfo():
	if _plugin:
		_plugin.requestReviewInfo();


func launchReviewFlow():
	if _plugin:
		_plugin.launchReviewFlow();