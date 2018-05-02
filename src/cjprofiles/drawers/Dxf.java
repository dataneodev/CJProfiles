package cjprofiles.drawers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Dxf {
	private static final Logger logger = Logger.getLogger( Dxf.class.getName() );
	private boolean openDxf;
	private boolean polyLineStart;
	private String LayerContinue = "CJCONTINUE";
	private String LayerHidden = "CJHIDDEN";
	private int scaleFac = 1;
	private lineType selectedLine = lineType.lineContinue;
	private List<String> dxfBody = new ArrayList<>();
	public enum lineType {lineContinue, lineHidden};
	

	public Dxf() {
		openDxf = true;
		polyLineStart = false;
		dxfBody.clear();
		logger.fine("startDxf()");
		
		String[] header = new String [] {
				"0", "SECTION", "2", "HEADER", "999", "dxf created by CJProfiles", "0", "ENDSEC",
				// TABLES
				"0", "SECTION","2","TABLES","0","TABLE","2","LTYPE","70","1",
					"0","LTYPE","2","CJCONTINUOUS","70","64","3","CJSolid line","72","65","73","0","40","0.000000",
					"0","LTYPE","2","CJHIDDEN","70","64","3","CJHidden line","72","65","73","2","40","30.0","49","12","49","-18","0","ENDTAB",
				"0", "TABLE","2","LAYER","70","6",
					"0","LAYER","2",LayerContinue,"70","64","62","3","6","CJCONTINUOUS",
					"0","LAYER","2",LayerHidden,"70","64","62","50","6","CJHIDDEN","0","ENDTAB",
				"0","TABLE","2","STYLE","70","0","0","ENDTAB","0","ENDSEC",
				// end TABLES
				"0", "SECTION", "2", "BLOCKS", "0", "ENDSEC",
				"0", "SECTION", "2", "ENTITIES" 
			  };
		for(int a = 0; a < header.length; a++) {
			dxfBody.add(header[a]);	
		}
	}
	
	public void endDxf() {
		logger.fine("endDxf.");
		if(openDxf == false) {
			logger.info("endDxf: openDxf == false");
			return;
		}
		if(polyLineStart == true) {
			polyLineStart = false;
			polyLineEnd();
		}
		// ends of ENTITIES
		dxfBody.add("0");
		dxfBody.add("ENDSEC");	
		// ends of file
		dxfBody.add("0");
		dxfBody.add("EOF");
		openDxf = false;
	}
	
	public void polyLineStart(lineType lineTyp) {
		logger.fine("polyLineStart.");
		if(polyLineStart == true) {
			logger.info("polyLineStart: polyLine is already open");
			return;
		}
		selectedLine = lineTyp;
		dxfBody.add("0");
		dxfBody.add("POLYLINE");
		dxfBody.add("8");
		if(selectedLine == lineType.lineContinue) {
			dxfBody.add(LayerContinue);
		} else {
			dxfBody.add(LayerHidden);
		}
		dxfBody.add( "66");
		dxfBody.add("1");
		dxfBody.add("40");
		dxfBody.add("0");
		dxfBody.add("41");
		dxfBody.add("0");
		polyLineStart = true;
	}
	
	public void polyLineEnd() {
		logger.fine("polyLineEnd.");
		if(polyLineStart == false) {
			logger.info("polyLineEnd: polyLine not started");
			return;
		}
		dxfBody.add("0");
		dxfBody.add("SEQEND");
		polyLineStart = false;
	}

	public void polyLineAdd(float x, float y, double Bulge) {
		logger.fine("polyLineAdd at x="+Float.toString(x)+" y="+Float.toString(y)+" Bulge="+Double.toString(Bulge));
		if(polyLineStart == false) {
			polyLineStart(lineType.lineContinue);
		}
		dxfBody.add("0");
		dxfBody.add("VERTEX");
		dxfBody.add("8");
		if(selectedLine == lineType.lineContinue) {
			dxfBody.add(LayerContinue);
		} else {
			dxfBody.add(LayerHidden);
		}
		dxfBody.add("10");
		dxfBody.add(Float.toString(scaleFac*x));
		dxfBody.add("20");
		dxfBody.add(Float.toString(scaleFac*y));
		dxfBody.add("30");
		dxfBody.add("0.0");
		if(Bulge!=0) {
			dxfBody.add("42");
			dxfBody.add(Double.toString(Bulge));	
		}
	}
	
	public String getDxfBody() {
		logger.fine("getDxfBody.");
		if(openDxf == true) {
			endDxf();
		}
		return String.join("\n", dxfBody);
	}
	
		
	void dxfCircle(int x, int y, float xco1, float yco1, float scaleRad, String layer) {
			/*
			  int oldLen = expDxf.length;
			  int newLen = (expDxf.length+12);
			  expDxf = expand(expDxf, newLen );
			  expDxf [oldLen] = "CIRCLE";
			  expDxf [oldLen+1] = "8";
			  expDxf [oldLen+2] = layer;
			  expDxf [oldLen+3] = "10";
			  expDxf [oldLen+4] = str(scaleFac*(xco1+x));
			  expDxf [oldLen+5] = "20";
			  expDxf [oldLen+6] = str(scaleFac*(yco1+y));
			  expDxf [oldLen+7] = "30";
			  expDxf [oldLen+8] = "0.0";
			  expDxf [oldLen+9] = "40";
			  expDxf [oldLen+10] = str(scaleFac * scaleRad);
			  expDxf [oldLen+11] = "0";
			  */
	}
		
}
