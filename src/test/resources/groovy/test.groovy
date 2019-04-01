import net.doublegsoft.appbase.ObjectMap

def param1 = (ObjectMap) binding.getVariable("param1")

return param1.get("hello")