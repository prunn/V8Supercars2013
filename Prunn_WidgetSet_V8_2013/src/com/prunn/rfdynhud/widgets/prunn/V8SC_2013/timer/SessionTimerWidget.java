package com.prunn.rfdynhud.widgets.prunn.V8SC_2013.timer;

import java.awt.Font;
import java.io.IOException;
import net.ctdp.rfdynhud.gamedata.GamePhase;
import net.ctdp.rfdynhud.gamedata.LiveGameData;
import net.ctdp.rfdynhud.gamedata.ScoringInfo;
import net.ctdp.rfdynhud.gamedata.SessionLimit;
import net.ctdp.rfdynhud.gamedata.SessionType;
import net.ctdp.rfdynhud.properties.ColorProperty;
import net.ctdp.rfdynhud.properties.FontProperty;
import net.ctdp.rfdynhud.properties.ImagePropertyWithTexture;
import net.ctdp.rfdynhud.properties.IntProperty;
import net.ctdp.rfdynhud.properties.PropertiesContainer;
import net.ctdp.rfdynhud.properties.PropertyLoader;
import net.ctdp.rfdynhud.render.DrawnString;
import net.ctdp.rfdynhud.render.DrawnString.Alignment;
import net.ctdp.rfdynhud.render.DrawnStringFactory;
import net.ctdp.rfdynhud.render.TextureImage2D;
import net.ctdp.rfdynhud.util.PropertyWriter;
import net.ctdp.rfdynhud.util.SubTextureCollector;
import net.ctdp.rfdynhud.util.TimingUtil;
import net.ctdp.rfdynhud.valuemanagers.Clock;
import net.ctdp.rfdynhud.values.BoolValue;
import net.ctdp.rfdynhud.values.EnumValue;
import net.ctdp.rfdynhud.values.FloatValue;
import net.ctdp.rfdynhud.values.IntValue;
import net.ctdp.rfdynhud.values.StringValue;
import net.ctdp.rfdynhud.widgets.base.widget.Widget;
import com.prunn.rfdynhud.widgets.prunn._util.PrunnWidgetSetv8SC_2013;

/**
 * @author Prunn
 * copyright@Prunn2011
 * 
 */
public class SessionTimerWidget extends Widget
{
	
	private final EnumValue<GamePhase> gamePhase = new EnumValue<GamePhase>();
    private final EnumValue<SessionLimit> sessionLimit = new EnumValue<SessionLimit>();
    private final IntValue LapsLeft = new IntValue();
    private final BoolValue IsFinalLap = new BoolValue();
    private final FloatValue sessionTime = new FloatValue(-1F, 0.1F);
    private DrawnString dsSession = null;
    private DrawnString dsOf = null;
    private DrawnString dsSessionLaps = null;
    private DrawnString dsSessionLapsLeft = null;
    private DrawnString dsSessionTimeLeft = null;
    private DrawnString dsInfo = null;
    private String strlaptime = "";
    private String strlapleft = "";
    private String strlaptotal = "";
    private final StringValue strLaptime = new StringValue( "" );
    private ImagePropertyWithTexture imgBG = new ImagePropertyWithTexture( "imgBG", "prunn/V8SC_2013/timer_bg.png" );
    private ImagePropertyWithTexture imgBGFinalLap = new ImagePropertyWithTexture( "imgBG", "prunn/V8SC_2013/finallap.png" );
    private ImagePropertyWithTexture imgBGQual = new ImagePropertyWithTexture( "imgBG", "prunn/V8SC_2013/qtime.png" );
    private ImagePropertyWithTexture imgBGQualEnd = new ImagePropertyWithTexture( "imgBG", "prunn/V8SC_2013/timer_finished.png" );
    protected final FontProperty f1_2011Font = new FontProperty("Main Font", PrunnWidgetSetv8SC_2013.V8_FONT_NAME);
    private final ColorProperty fontColor1 = new ColorProperty( "fontColor1", PrunnWidgetSetv8SC_2013.FONT_COLOR1_NAME );
    private final ColorProperty fontColor2 = new ColorProperty("fontColor2", PrunnWidgetSetv8SC_2013.FONT_COLOR2_NAME);
    private final FontProperty posFontTH = new FontProperty("v8thFont", PrunnWidgetSetv8SC_2013.V8_FONT_NAME_TH);
    private IntProperty fontyoffset = new IntProperty("Y Font Offset", 4);
    
   
    
    @Override
    public void onCockpitEntered( LiveGameData gameData, boolean isEditorMode )
    {
        super.onCockpitEntered( gameData, isEditorMode );
        LapsLeft.reset();
    	gamePhase.reset();
        String cpid = "Y29weXJpZ2h0QFBydW5uMjAxMQ";
        if(!isEditorMode)
            log(cpid);
        
    }
    public void onSessionStarted(SessionType sessionType, LiveGameData gameData, boolean isEditorMode)
    {
        super.onSessionStarted(sessionType, gameData, isEditorMode);
        gamePhase.reset();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initSubTextures( LiveGameData gameData, boolean isEditorMode, int widgetInnerWidth, int widgetInnerHeight, SubTextureCollector collector )
    {
    }
    
    @Override
    protected void initialize( LiveGameData gameData, boolean isEditorMode, DrawnStringFactory drawnStringFactory, TextureImage2D texture, int width, int height )
    {
        int fh = TextureImage2D.getStringHeight( "0%C", f1_2011Font );
        int fh2 = TextureImage2D.getStringHeight( "0%C", posFontTH );
        int rowHeight = height;
        
        dsInfo = drawnStringFactory.newDrawnString( "dsInfo", width*22/100, rowHeight/2 - fh/2 + fontyoffset.getValue(), Alignment.CENTER, false, f1_2011Font.getFont(), isFontAntiAliased(), fontColor1.getColor() );
        dsSession = drawnStringFactory.newDrawnString( "dsSession",width*50/100 , rowHeight/2 - fh/2 + fontyoffset.getValue(), Alignment.CENTER, false, f1_2011Font.getFont(), isFontAntiAliased(), fontColor1.getColor(), null, "" );
        
        dsOf = drawnStringFactory.newDrawnString( "dsSession",width*66/100 , rowHeight/2 - fh2 * 45/100 + fontyoffset.getValue(), Alignment.CENTER, false, posFontTH.getFont(), isFontAntiAliased(), fontColor2.getColor(), null, "" );
        dsSessionLaps = drawnStringFactory.newDrawnString( "dsSession",width*80/100 , rowHeight/2 - fh/2 + fontyoffset.getValue(), Alignment.CENTER, false, f1_2011Font.getFont(), isFontAntiAliased(), fontColor2.getColor(), null, "" );
        dsSessionLapsLeft = drawnStringFactory.newDrawnString( "dsSession",width*53/100 , rowHeight/2 - fh/2 + fontyoffset.getValue(), Alignment.CENTER, false, f1_2011Font.getFont(), isFontAntiAliased(), fontColor2.getColor(), null, "" );
        dsSessionTimeLeft = drawnStringFactory.newDrawnString( "dsSession",width*75/200 , rowHeight/2 - fh/2 + fontyoffset.getValue(), Alignment.CENTER, false, f1_2011Font.getFont(), isFontAntiAliased(), fontColor1.getColor(), null, "" );
            
       
        imgBG.updateSize( width, height, isEditorMode );
        imgBGFinalLap.updateSize( width, height, isEditorMode );
        imgBGQual.updateSize( width * 3/4, height, isEditorMode );
        imgBGQualEnd.updateSize( width, height, isEditorMode );
        
    }
    
    protected Boolean updateVisibility(LiveGameData gameData, boolean isEditorMode)
    {
        super.updateVisibility(gameData, isEditorMode);
        ScoringInfo scoringInfo = gameData.getScoringInfo();
        
        gamePhase.update(scoringInfo.getGamePhase());
        sessionLimit.update( scoringInfo.getViewedVehicleScoringInfo().getSessionLimit() );
        if(scoringInfo.getLeadersVehicleScoringInfo().getCurrentLap() < scoringInfo.getMaxLaps())
            IsFinalLap.update( true );
        else
            IsFinalLap.update( false );
        
        if((sessionLimit.hasChanged() || IsFinalLap.hasChanged() || gamePhase.hasChanged())&& !isEditorMode)
            forceCompleteRedraw(true);
        
        if( scoringInfo.getGamePhase() == GamePhase.FORMATION_LAP )
            return false;
        if( scoringInfo.getGamePhase() == GamePhase.STARTING_LIGHT_COUNTDOWN_HAS_BEGUN && scoringInfo.getEndTime() <= scoringInfo.getSessionTime() )
            return false;
        
        return true;
        
    }
    @Override
    protected void drawBackground( LiveGameData gameData, boolean isEditorMode, TextureImage2D texture, int offsetX, int offsetY, int width, int height, boolean isRoot )
    {
        super.drawBackground( gameData, isEditorMode, texture, offsetX, offsetY, width, height, isRoot );
        ScoringInfo scoringInfo = gameData.getScoringInfo();
        
        if(sessionLimit.getValue() == SessionLimit.LAPS && (scoringInfo.getSessionType() == SessionType.RACE1 || scoringInfo.getSessionType() == SessionType.RACE2 ||scoringInfo.getSessionType() == SessionType.RACE3 || scoringInfo.getSessionType() == SessionType.RACE4))
        {
            if(scoringInfo.getLeadersVehicleScoringInfo().getCurrentLap() < scoringInfo.getMaxLaps())
                texture.clear( imgBG.getTexture(), offsetX, offsetY, false, null );
            else if(gamePhase.getValue() != GamePhase.SESSION_OVER )
                texture.clear( imgBGFinalLap.getTexture(), offsetX, offsetY, false, null );
            /*else
                texture.clear( imgBGQualEnd.getTexture(), offsetX, offsetY, false, null );*/
         }
        else
        {
            if(gamePhase.getValue() == GamePhase.SESSION_OVER || (scoringInfo.getEndTime() <= sessionTime.getValue() && gamePhase.getValue() != GamePhase.STARTING_LIGHT_COUNTDOWN_HAS_BEGUN ) )
                texture.clear( imgBGQualEnd.getTexture(), offsetX, offsetY, false, null ); 
            else
                texture.clear( imgBGQual.getTexture(), offsetX, offsetY, false, null );
        }
    }
    
    @Override
    protected void drawWidget( Clock clock, boolean needsCompleteRedraw, LiveGameData gameData, boolean isEditorMode, TextureImage2D texture, int offsetX, int offsetY, int width, int height )
    {
        ScoringInfo scoringInfo = gameData.getScoringInfo();
        
        if (scoringInfo.getSessionType().isRace() && scoringInfo.getViewedVehicleScoringInfo().getSessionLimit() == SessionLimit.LAPS)
    	{
            if(scoringInfo.getLeadersVehicleScoringInfo().getCurrentLap() > scoringInfo.getMaxLaps())
                LapsLeft.update(scoringInfo.getMaxLaps());
            else
                LapsLeft.update(scoringInfo.getLeadersVehicleScoringInfo().getCurrentLap());
        
            if ( needsCompleteRedraw || LapsLeft.hasChanged() )
	    	{
	    	    strlapleft = LapsLeft.getValueAsString();
	    	    strlaptotal = String.valueOf( scoringInfo.getMaxLaps() );
	    	    strlaptime = LapsLeft.getValueAsString() + "   " + scoringInfo.getMaxLaps();
	    	}
	    	
	    	strLaptime.update( strlaptime );
	    	if ( needsCompleteRedraw || ( clock.c() && strLaptime.hasChanged() ) )
	        {
	    	    if(scoringInfo.getLeadersVehicleScoringInfo().getCurrentLap() < scoringInfo.getMaxLaps())
	    	    {
	    	        dsSessionLaps.draw( offsetX, offsetY, strlaptotal, texture );
	    	        dsOf.draw( offsetX, offsetY, "OF", texture );
                    dsSessionLapsLeft.draw( offsetX, offsetY, strlapleft, texture );
	    	        dsInfo.draw( offsetX, offsetY, "LAP", texture );
	    	    }
	    	    else if(gamePhase.getValue() != GamePhase.SESSION_OVER )
	    	    {
	    	        /*dsSessionLaps.draw( offsetX, offsetY, "", texture );
                    dsSessionLapsLeft.draw( offsetX, offsetY, "", texture );*/
                    dsSession.draw( offsetX, offsetY, "FINAL LAP", texture );
	    	        //dsInfo.draw( offsetX, offsetY, "",texture );
	    	    }
                /*else
                {
                    dsSessionLaps.draw( offsetX, offsetY, "", texture );
                    dsSessionLapsLeft.draw( offsetX, offsetY, "", texture );
                    dsSession.draw( offsetX, offsetY, "", texture );
                    dsInfo.draw( offsetX, offsetY, "",texture );
                }*/
	        }
		}
    	else // Test day only
    		if(scoringInfo.getSessionType().isTestDay())
    		{
    		    strlaptime = "LAP " + (scoringInfo.getViewedVehicleScoringInfo().getLapsCompleted() + 1);
    		    strLaptime.update( strlaptime );
                
    		    if ( needsCompleteRedraw || ( clock.c() && strLaptime.hasChanged() ) )
    		    {
    		        dsSessionTimeLeft.draw( offsetX, offsetY, strlaptime, texture );
    		    }
    	        
    		}
    		else // any other timed session (Race, Qualify, Practice)
	    	{
    		    /*if(scoringInfo.getSessionType() == SessionType.RACE1 || scoringInfo.getSessionType() == SessionType.RACE2 || scoringInfo.getSessionType() == SessionType.RACE3 || scoringInfo.getSessionType() == SessionType.RACE4)
    		        strInfo = "Time";*/
    	               
	    		sessionTime.update(scoringInfo.getSessionTime());
	    		float endTime = scoringInfo.getEndTime();
	    		
                 
	    		if ( needsCompleteRedraw || sessionTime.hasChanged() )
		        {
		        	if(gamePhase.getValue() == GamePhase.SESSION_OVER || (endTime <= sessionTime.getValue() && gamePhase.getValue() != GamePhase.STARTING_LIGHT_COUNTDOWN_HAS_BEGUN ) )
		        	{    
		        	    strlaptime = "";
	                    dsSession.draw( offsetX, offsetY, "00:00", texture );
		        	}
			        else
        			    if(gamePhase.getValue() == GamePhase.STARTING_LIGHT_COUNTDOWN_HAS_BEGUN && endTime <= sessionTime.getValue())
		        		    strlaptime = "00:00";
		        		else
		        		{
		        		    
		        		    strlaptime = TimingUtil.getTimeAsString(endTime - sessionTime.getValue(), true, false);
		        	
        		        	if (strlaptime.charAt( 0 ) == '0')
        		        	    strlaptime = strlaptime.substring( 1 );
        		        	if (strlaptime.charAt( 0 ) == '0')
                                strlaptime = strlaptime.substring( 2 );
        		        	//if (strlaptime.charAt( 0 ) == '0')
                            //    strlaptime = strlaptime.substring( 1 );
		        		}
		        }
	    		
	    		strLaptime.update( strlaptime );
        
	    		if ( needsCompleteRedraw || ( clock.c() && strLaptime.hasChanged() ) )
                {
	    		    if(!strlaptime.equals( "" ))
	    		        dsSessionTimeLeft.draw( offsetX, offsetY, strlaptime, texture );
                }
	    	
	    	}
    }
    
    @Override
    public void saveProperties( PropertyWriter writer ) throws IOException
    {
        super.saveProperties( writer );
        writer.writeProperty( f1_2011Font, "timeFont" );
        writer.writeProperty( fontColor1, "" );
        writer.writeProperty( fontColor2, "" );
        writer.writeProperty( fontyoffset, "" );
        writer.writeProperty( posFontTH, "" );
    }
    
    @Override
    public void loadProperty( PropertyLoader loader )
    {
        super.loadProperty( loader );
        if ( loader.loadProperty( f1_2011Font ) );
        else if ( loader.loadProperty( fontColor1 ) );
        else if ( loader.loadProperty( posFontTH ) );
        else if ( loader.loadProperty( fontColor2 ) );
        else if ( loader.loadProperty( fontyoffset ) );
    }
    
    @Override
    public void getProperties( PropertiesContainer propsCont, boolean forceAll )
    {
        super.getProperties( propsCont, forceAll );
        
        propsCont.addGroup( "Font" );
        propsCont.addProperty( f1_2011Font );
        propsCont.addProperty( posFontTH );
        propsCont.addProperty( fontColor1 );
        propsCont.addProperty( fontColor2 );
        propsCont.addGroup( "Session Names" );
        propsCont.addProperty( fontyoffset );
        
    }
    @Override
    protected boolean canHaveBorder()
    {
        return ( false );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void prepareForMenuItem()
    {
        super.prepareForMenuItem();
        
        getFontProperty().setFont( "Dialog", Font.PLAIN, 6, false, true );
    }
    
    public SessionTimerWidget()
    {
        super( PrunnWidgetSetv8SC_2013.INSTANCE, PrunnWidgetSetv8SC_2013.WIDGET_PACKAGE_V8, 19.0f, 5.0f );
        getBackgroundProperty().setColorValue( "#00000000" );
        getFontProperty().setFont( PrunnWidgetSetv8SC_2013.V8_FONT_NAME );
        //getBorderProperty().setBorder( null );
    }
}
