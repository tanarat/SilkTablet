{
	"package": "org.hsc.silk.model",
    "prefix": "SILK",
    "database": "silk.db",
    "tables": ["checklist", 
    			"checklist_item", 
    			"regulation",
    			"regulation_item",
    			"sheet",
    			"sheet_item",
    			"bpartner",
    			"bp_location",
    			"audit_type",
    			"remark_choice",
    			"setting",
    			"user"
    			],
    "checklist": {
        "columns": [
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
    "checklist_item": {
        "columns": [
        {
            "name": "name",
            "type": "varchar(100)"
        },
        {
            "name": "value",
            "type": "varchar(10)"
        },
        {
            "name": "checklist_id",
            "type": "integer"
        },
        {
            "name": "regulation_item_id",
            "type": "integer"
        }
        
        ]
    },
    "regulation": {
        "columns": [
        {
            "name": "name",
            "type": "varchar(100)"
        }
        ]
    },
    "regulation_item": {
        "columns": [
        {
            "name": "name",
            "type": "varchar(100)"
        },
        {
            "name": "value",
            "type": "varchar(10)"
        },
        {
            "name": "regulation_id",
            "type": "integer"
        },
        {
            "name": "parent_item_id",
            "type": "integer"
        },
        {
            "name": "is_group",
            "type": "boolean"
        }
        ]
    },
    "sheet": {
        "columns": [
        {
            "name": "name",
            "type": "varchar(100)"
        },
        {
            "name": "checklist_id",
            "type": "integer"
        },
        {
            "name": "bpartner_id",
            "type": "integer"
        },
        {
            "name": "bp_location_id",
            "type": "integer"
        },
        {
            "name": "audit_type",
            "type": "integer"
        },
        {
            "name": "audit_date",
            "type": "timestamp"
        },
        {
            "name": "audit_timeslot",
            "type": "integer"
        },
        {
            "name": "status",
            "type": "integer"
        }
        
        ]
    },
    "sheet_item": {
        "columns": [
        {
            "name": "sheet_id",
            "type": "integer"
        },
        {
            "name": "checklist_item_id",
            "type": "integer"
        },
        {
            "name": "answer",
            "type": "integer"
        },
        {
            "name": "remark",
            "type": "varchar(500)"
        }
        ]
    },
    "bpartner": {
        "columns": [
        {
            "name": "name",
            "type": "varchar(100)"
        }
        ]
    },
    "bp_location": {
        "columns": [
        {
        	"name": "name",
        	"type": "varchar(100)"
        },
        {
            "name": "phone",
            "type": "varchar(15)"
        },
        {
            "name": "phone2",
            "type": "varchar(15)"
        },
        {
            "name": "fax",
            "type": "varchar(15)"
        },
        {
            "name": "address1",
            "type": "varchar(100)"
        },
        {
            "name": "address2",
            "type": "varchar(100)"
        },
        {
            "name": "address3",
            "type": "varchar(100)"
        },
        {
            "name": "address4",
            "type": "varchar(100)"
        },
        {
            "name": "postal",
            "type": "varchar(5)"
        },
        {
            "name": "city",
            "type": "varchar(100)"
        }
        ]
    },
    "audit_type": {
        "columns": [
        {
            "name": "name",
            "type": "varchar(100)"
        }
        ]
    },
    "remark_choice": {
        "columns": [
        {
            "name": "name",
            "type": "varchar(100)"
        },
        {
            "name": "value",
            "type": "varchar(10)"
        },
        {
            "name": "description",
            "type": "varchar(100)"
        },
        {
            "name": "checklist_item_id",
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