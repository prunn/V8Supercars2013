package com.prunn.rfdynhud.widgets.prunn.V8SC_2013.qualifinfo;

import java.awt.Font;
import java.io.IOException;

import com.prunn.rfdynhud.widgets.prunn._util.PrunnWidgetSetv8SC_2013;
import com.prunn.rfdynhud.widgets.prunn.V8SC_2013.qtime.QualTimeWidget;

import net.ctdp.rfdynhud.gamedata.LiveGameData;
import net.ctdp.rfdynhud.gamedata.ScoringInfo;
import net.ctdp.rfdynhud.gamedata.VehicleScoringInfo;
import net.ctdp.rfdynhud.properties.ColorProperty;
import net.ctdp.rfdynhud.properties.DelayProperty;
import net.ctdp.rfdynhud.properties.FontProperty;
import net.ctdp.rfdynhud.properties.ImagePropertyWithTexture;
import net.ctdp.rfdynhud.properties.IntProperty;
import net.ctdp.rfdynhud.properties.PropertiesContainer;
import net.ctdp.rfdynhud.properties.PropertyLoader;
import net.ctdp.rfdynhud.render.DrawnString;
import net.ctdp.rfdynhud.render.DrawnStringFactory;
import net.ctdp.rfdynhud.render.TextureImage2D;
import net.ctdp.rfdynhud.render.DrawnString.Alignment;
import net.ctdp.rfdynhud.util.NumberUtil;
import net.ctdp.rfdynhud.util.PropertyWriter;
import net.ctdp.rfdynhud.util.SubTextureCollector;
import net.ctdp.rfdynhud.valuemanagers.Clock;
import net.ctdp.rfdynhud.values.BoolValue;
import net.ctdp.rfdynhud.values.FloatValue;
import net.ctdp.rfdynhud.values.IntValue;
import net.ctdp.rfdynhud.values.StringValue;
import net.ctdp.rfdynhud.widgets.base.widget.Widget;

/**
 * @author Prunn
 * copyright@Prunn2011
 * 
 */


public class QualifInfoWidget extends Widget
{   
    private DrawnString dsName = null;
    private DrawnString dsLastName = null;
    private DrawnString dsTeam = null;
    private final ImagePropertyWithTexture imgFord = new ImagePropertyWithTexture( "imgTeam", "prunn/V8SC_2013/info.png" );
    
    private final FontProperty InfoNameFont = new FontProperty("InfoNameFont", PrunnWidgetSetv8SC_2013.INFO_NAME_FONT);
    protected final FontProperty f1_2011Font = new FontProperty("Main Font", PrunnWidgetSetv8SC_2013.V8_FONT_NAME);
    private final ColorProperty fontColor1 = new ColorProperty("fontColor1", PrunnWidgetSetv8SC_2013.FONT_COLOR1_NAME);
    private final ColorProperty fontColor2 = new ColorProperty("fontColor2", PrunnWidgetSetv8SC_2013.FONT_COLOR2_NAME);
    private final ColorProperty fontColorGrey = new ColorProperty("fontColorGrey", PrunnWidgetSetv8SC_2013.FONT_COLOR_GREY_NAME);
    private final FloatValue sessionTime = new FloatValue(-1F, 0.1F);
    
    private final DelayProperty visibleTime;
    private long visibleEnd;
    private IntValue cveh = new IntValue();
    private BoolValue cpit = new BoolValue();
    private StringValue team = new StringValue();
    private StringValue name = new StringValue();
    private StringValue pos = new StringValue();
    private IntProperty fontyoffset = new IntProperty("Y Font Offset", 4);
    
    
    @Override
    public void onRealtimeEntered( LiveGameData gameData, boolean isEditorMode )
    {
        super.onRealtimeEntered( gameData, isEditorMode );
        String cpid = "Y29weXJpZ2h0QFBydW5uMjAxMQ";
        if(!isEditorMode)
            log(cpid);
    }

    public String FirstName( String driverName )
    {
        int sp = driverName.lastIndexOf( ' ' );
        if ( sp == -1 )
            return "";//( driverName );
        String sf = driverName.substring(0, sp );
        
        return ( sf );
    }
    public String LastName( String driverName )
    {
        int sp = driverName.lastIndexOf( ' ' );
        if ( sp == -1 )
            return ( driverName );
        
        
        String sf = driverName.substring( sp + 1 );
        
        return ( sf );
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
        
        team.update( "" );
        name.update( "" ); 
        pos.update( "" );
        
        int fh = TextureImage2D.getStringHeight( "0", f1_2011Font );
        int fhPos = TextureImage2D.getStringHeight( "0", InfoNameFont );
        
        imgFord.updateSize( width, height, isEditorMode );
        
        int top1 = height*17/100 - fh / 2;
        int top2 = height*46/100 - fhPos / 2;
        int top3 = height*87/100 - fh / 2;
        dsName = drawnStringFactory.newDrawnString( "dsName", width*5/100, top1 + fontyoffset.getValue(), Alignment.LEFT, false, f1_2011Font.getFont(), isFontAntiAliased(), fontColor2.getColor() );
        dsLastName = drawnStringFactory.newDrawnString( "dsName", width*5/100, top2 + fontyoffset.getValue(), Alignment.LEFT, false, InfoNameFont.getFont(), isFontAntiAliased(), fontColorGrey.getColor() );
        dsTeam = drawnStringFactory.newDrawnString( "dsTeam", width*5/100, top3 + fontyoffset.getValue(), Alignment.LEFT, false, f1_2011Font.getFont(), isFontAntiAliased(), fontColor1.getColor() );
        
    }
    
    @Override
    protected Boolean updateVisibility(LiveGameData gameData, boolean isEditorMode)
    {
        
        
        super.updateVisibility(gameData, isEditorMode);
        ScoringInfo scoringInfo = gameData.getScoringInfo();
        cveh.update(scoringInfo.getViewedVehicleScoringInfo().getDriverId());
        cpit.update(scoringInfo.getViewedVehicleScoringInfo().isInPits());
        
        if(QualTimeWidget.visible())
            return false;
        
        if((cveh.hasChanged() || cpit.hasChanged()) && !isEditorMode)
        {
            forceCompleteRedraw(true);
            visibleEnd = scoringInfo.getSessionNanos() + visibleTime.getDelayNanos();
            return true;
        }
        
        if(scoringInfo.getSessionNanos() < visibleEnd || cpit.getValue())
            return true;
        
        
        return false;	
    }
    @Override
    protected void drawBackground( LiveGameData gameData, boolean isEditorMode, TextureImage2D texture, int offsetX, int offsetY, int width, int height, boolean isRoot )
    {
        super.drawBackground( gameData, isEditorMode, texture, offsetX, offsetY, width, height, isRoot );
        texture.clear( imgFord.getTexture(), offsetX, offsetY, false, null );
    }
    
    
    @Override
    protected void drawWidget( Clock clock, boolean needsCompleteRedraw, LiveGameData gameData, boolean isEditorMode, TextureImage2D texture, int offsetX, int offsetY, int width, int height )
    {
        ScoringInfo scoringInfo = gameData.getScoringInfo();
    	sessionTime.update(scoringInfo.getSessionTime());
    	
    	if ( needsCompleteRedraw )
        {
    	    VehicleScoringInfo currentcarinfos = gameData.getScoringInfo().getViewedVehicleScoringInfo();
        	name.update( currentcarinfos.getDriverName() );
            pos.update( NumberUtil.formatFloat( currentcarinfos.getPlace(false), 0, true));
            if(currentcarinfos.getVehicleInfo() != null)
                team.update( currentcarinfos.getVehicleInfo().getFullTeamName() );
            else
                team.update( currentcarinfos.getVehicleClass()); 
            dsName.draw( offsetX, offsetY, FirstName(name.getValue().toUpperCase()), texture );
            dsLastName.draw( offsetX, offsetY, LastName(name.getValue().toUpperCase()), texture );
            dsTeam.draw( offsetX, offsetY, team.getValue(), texture );
                
        }
         
    }
    
    
    @Override
    public void saveProperties( PropertyWriter writer ) throws IOException
    {
        super.saveProperties( writer );
        writer.writeProperty( f1_2011Font, "" );
        writer.writeProperty( InfoNameFont, "" );
        writer.writeProperty( fontColor1, "" );
        writer.writeProperty( fontColor2, "" );
        writer.writeProperty( fontColorGrey, "" );
        writer.writeProperty(visibleTime, "");
        writer.writeProperty( fontyoffset, "" );
        
        
    }
    
    @Override
    public void loadProperty( PropertyLoader loader )
    {
        super.loadProperty( loader );
        if ( loader.loadProperty( f1_2011Font ) );
        else if ( loader.loadProperty( InfoNameFont ) );
        else if ( loader.loadProperty( fontColor1 ) );
        else if ( loader.loadProperty( fontColor2 ) );
        else if ( loader.loadProperty( fontColorGrey ) );
        else if( loader.loadProperty(visibleTime));
        else if ( loader.loadProperty( fontyoffset ) );
        
        
    }
    
    @Override
    public void getProperties( PropertiesContainer propsCont, boolean forceAll )
    {
        super.getProperties( propsCont, forceAll );
        
        propsCont.addGroup( "Colors" );
        propsCont.addProperty( f1_2011Font );
        propsCont.addProperty( InfoNameFont );
        propsCont.addProperty( fontColor1 );
        propsCont.addProperty( fontColor2 );
        propsCont.addProperty( fontColorGrey );
        propsCont.addProperty(visibleTime);
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
    
    public QualifInfoWidget()
    {
        super( PrunnWidgetSetv8SC_2013.INSTANCE, PrunnWidgetSetv8SC_2013.WIDGET_PACKAGE_V8, 40.0f, 7.0f );
        visibleTime = new DelayProperty("visibleTime", net.ctdp.rfdynhud.properties.DelayProperty.DisplayUnits.SECONDS, 6);
        visibleEnd = 0x8000000000000000L;
        getBackgroundProperty().setColorValue( "#00000000" );
        getFontProperty().setFont( PrunnWidgetSetv8SC_2013.V8_FONT_NAME );
    }
    
}
