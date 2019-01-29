package aldwin.tablante.com.appblock.Account.AppBlock.Model

class MyDevices(MODEL:String, ID : String) {
   var NAME = MODEL
   var  ID = ID
   var parentId = ""
   var parentname= ""
   var parentno = ""
   var parentsno =""
   var parentemail= ""

   constructor( ) : this("","")
}