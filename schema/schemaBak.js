{
    "package": "org.hsc.silk.model",
    "prefix": "SILK",
    "database": "silk.db",
    "tables":["checklist","setting","user"],
    "checklist":{
    	"columns":[
    	 {
            "name": "name",
            "type": "varchar(100)"
         },
         {
            "name": "description",
            "type": "varchar(100)"
         },
         {
            "name": "regulation_id",
            "type": "integer"
         }
    	 ]
    },
    "setting":{
    	"columns":[
    	 {
    	     "name": "data_version",
    	     "type": "integer"
    	 },
    	 {
    		 "name": "is_default",
    		 "type": "boolean"
    	 }
    	 ]
    },
	"user":{
		"columns":[
		 {
			 "name": "name",
			 "type": "varchar(100)"
		 },
		 {
			 "name": "password",
			 "type": "varchar(100)"
		 },
		 {
			 "name": "title",
			 "type": "varchar(30)"
		 },
		 {
			 "name": "full_name",
			 "type": "varchar(100)"
		 }
		 ]
	}
    
}