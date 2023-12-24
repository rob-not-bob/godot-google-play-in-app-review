@tool
extends EditorPlugin

var _export_plugin : AndroidExportPlugin

func _enter_tree():
	_add_plugin();
	_add_autoloads();


func _exit_tree():
	_remove_plugin();
	_remove_autoloads();


func _add_plugin():
	_export_plugin = AndroidExportPlugin.new()
	add_export_plugin(_export_plugin)


func _remove_plugin():
	remove_export_plugin(_export_plugin);
	_export_plugin = null;


const PLUGIN_AUTOLOAD = "GooglePlayInAppReview";
func _add_autoloads() -> void:
	add_autoload_singleton(PLUGIN_AUTOLOAD, "res://addons/GooglePlayInAppReview/autoloads/GooglePlayInAppReview.gd");


func _remove_autoloads() -> void:
	remove_autoload_singleton(PLUGIN_AUTOLOAD);

class AndroidExportPlugin extends EditorExportPlugin:
	var _plugin_name = "GooglePlayInAppReview"

	func _supports_platform(platform) -> bool:
		return platform is EditorExportPlatformAndroid

	func _get_android_libraries(_platform, debug) -> PackedStringArray:
		if debug:
			return PackedStringArray([_plugin_name + "/bin/debug/" + _plugin_name + "-debug.aar"])
		else:
			return PackedStringArray([_plugin_name + "/bin/release/" + _plugin_name + "-release.aar"])

	func _get_android_dependencies(platform: EditorExportPlatform, _debug: bool) -> PackedStringArray:
		if not _supports_platform(platform):
			return PackedStringArray()

		return PackedStringArray([
			"com.google.android.play:review:2.0.1",
			"com.google.android.play:review-ktx:2.0.1"
		])

	func _get_name():
		return _plugin_name
