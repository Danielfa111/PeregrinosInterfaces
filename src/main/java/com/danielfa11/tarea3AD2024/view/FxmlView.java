package com.danielfa11.tarea3AD2024.view;

import java.util.ResourceBundle;

public enum FxmlView {
	
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
//			/tarea3AD2024base/src/main/java/com/luisdbb/tarea3AD2024base/view/FxmlView.java
//			/tarea3AD2024base/src/main/resources/fxml/Login.fxml
		}
	},
	REGISTRO{

		@Override
		public String getTitle() {
			// TODO Auto-generated method stub
			return getStringFromResourceBundle("registro.title");
		}

		@Override
		public String getFxmlFile() {
			// TODO Auto-generated method stub
			return "/fxml/Registro.fxml";
		}
		
	},
	ADMINISTRADOR{
		@Override
		public String getTitle() {
			// TODO Auto-generated method stub
			return getStringFromResourceBundle("administrador.title");
		}

		@Override
		public String getFxmlFile() {
			// TODO Auto-generated method stub
			return "/fxml/Administrador.fxml";
		}
	},
	PEREGRINO{
		@Override
		public String getTitle() {
			// TODO Auto-generated method stub
			return getStringFromResourceBundle("peregrino.title");
		}

		@Override
		public String getFxmlFile() {
			// TODO Auto-generated method stub
			return "/fxml/Peregrino.fxml";
		}
	},
	RESPONSABLE{
		@Override
		public String getTitle() {
			// TODO Auto-generated method stub
			return getStringFromResourceBundle("responsable.title");
		}

		@Override
		public String getFxmlFile() {
			// TODO Auto-generated method stub
			return "/fxml/Responsable.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
