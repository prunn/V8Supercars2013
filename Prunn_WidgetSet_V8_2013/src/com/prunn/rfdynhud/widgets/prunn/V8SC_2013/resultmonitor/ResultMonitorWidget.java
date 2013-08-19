package com.prunn.rfdynhud.widgets.prunn.V8SC_2013.resultmonitor;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import net.ctdp.rfdynhud.gamedata.FinishStatus;
import net.ctdp.rfdynhud.gamedata.LiveGameData;
import net.ctdp.rfdynhud.gamedata.ScoringInfo;
import net.ctdp.rfdynhud.gamedata.SessionType;
import net.ctdp.rfdynhud.gamedata.VehicleScoringInfo;
import net.ctdp.rfdynhud.properties.BooleanProperty;
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


public class ResultMonitorWidget extends Widget
{
    private DrawnString[] dsPos = null;
    private DrawnString[] dsName = null;
    private DrawnString[] dsTime = null;
    private DrawnString dsTrack = null;
    
    private final ImagePropertyWithTexture imgTrack = new ImagePropertyWithTexture( "imgTrack", "prunn/V8SC_2013/results_header.png" );
    private final ImagePropertyWithTexture imgFord = new ImagePropertyWithTexture( "imgFirst", "prunn/V8SC_2013/results.png" );
    private TextureImage2D texManufacturer = null;
    private final ImagePropertyWithTexture imgManufacturer = new ImagePropertyWithTexture( "imgManufacturer", "prunn/V8SC_2013/ford.png" );
    
    protected final FontProperty f1_2011Font = new FontProperty("Main Font", PrunnWidgetSetv8SC_2013.V8_FONT_NAME);
    protected final FontProperty v8sc_huge_font = new FontProperty("Title Font", PrunnWidgetSetv8SC_2013.V8_HUGE_FONT_NAME);
    private final ColorProperty fontColor3 = new ColorProperty( "fontColor3", PrunnWidgetSetv8SC_2013.FONT_COLOR3_NAME );
    private final ColorProperty fontColor2 = new ColorProperty( "fontColor2", PrunnWidgetSetv8SC_2013.FONT_COLOR2_NAME );
    private final ColorProperty fontColorGrey = new ColorProperty( "fontColorGrey", PrunnWidgetSetv8SC_2013.FONT_COLOR_GREY_NAME );
    
    private final IntProperty numVeh = new IntProperty( "numberOfVehicles", 10 );
    private IntProperty fontyoffset = new IntProperty("Y Font Offset", 4);
    private IntProperty fontxposoffset = new IntProperty("X Position Font Offset", 0);
    private IntProperty fontxnameoffset = new IntProperty("X Name Font Offset", 0);
    private IntProperty fontxtimeoffset = new IntProperty("X Time Font Offset", 0);
    
    private IntValue[] positions = null;
    private StringValue[] driverNames = null;
    private FloatValue[] gaps = null;
    private BooleanProperty AbsTimes = new BooleanProperty("Use absolute times", false) ;
    private int NumOfPNG = 0;
    private String[] listPNG;
    
    
    @Override
    public void onRealtimeEntered( LiveGameData gameData, boolean isEditorMode )
    {
        super.onRealtimeEntered( gameData, isEditorMode );
        String cpid = "Y29weXJpZ2h0QFBydW5uMjAxMQ";
        if(!isEditorMode)
            log(cpid);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initSubTextures( LiveGameData gameData, boolean isEditorMode, int widgetInnerWidth, int widgetInnerHeight, SubTextureCollector collector )
    {
    }
    
    private void initValues()
    {
        int maxNumItems = numVeh.getValue();
        
        if ( ( positions != null ) && ( positions.length == maxNumItems ) )
            return;
        
        gaps = new FloatValue[maxNumItems];
        positions = new IntValue[maxNumItems];
        driverNames = new StringValue[maxNumItems];
        
        for(int i=0;i < maxNumItems;i++)
        { 
            positions[i] = new IntValue();
            driverNames[i] = new StringValue();
            gaps[i] = new FloatValue();
        }
        
        
    }
    
    @Override
    protected void initialize( LiveGameData gameData, boolean isEditorMode, DrawnStringFactory drawnStringFactory, TextureImage2D texture, int width, int height )
    {
        int maxNumItems = numVeh.getValue();
        int fh = TextureImage2D.getStringHeight( "0%C", getFontProperty() );
        int fh2 = TextureImage2D.getStringHeight( "0%C", v8sc_huge_font );
        int rowHeight = height / (maxNumItems + 2);
        
        imgTrack.updateSize( width, rowHeight*2, isEditorMode );
        imgFord.updateSize( width, rowHeight, isEditorMode );
        Color whiteFontColor = fontColor2.getColor();
        
        dsPos = new DrawnString[maxNumItems];
        dsName = new DrawnString[maxNumItems];
        dsTime = new DrawnString[maxNumItems];
        
        
        int top = ( rowHeight - fh ) / 2;
        top += rowHeight;
        
        dsTrack = drawnStringFactory.newDrawnString( "dsTrack", width*3/100, top - fh2*1/3 + fontyoffset.getValue(), Alignment.LEFT, false, v8sc_huge_font.getFont(), isFontAntiAliased(), fontColorGrey.getColor() );
        top += rowHeight;
        
        for(int i=0;i < maxNumItems;i++)
        {
            dsPos[i] = drawnStringFactory.newDrawnString( "dsPos", width*8/200 + fontxposoffset.getValue(), top + fontyoffset.getValue(), Alignment.CENTER, false, f1_2011Font.getFont(), isFontAntiAliased(), fontColor3.getColor() );
            dsName[i] = drawnStringFactory.newDrawnString( "dsName", width*20/100 + fontxnameoffset.getValue(), top + fontyoffset.getValue(), Alignment.LEFT, false, f1_2011Font.getFont(), isFontAntiAliased(), whiteFontColor );
            dsTime[i] = drawnStringFactory.newDrawnString( "dsTime",  width*91/100 + fontxtimeoffset.getValue(), top + fontyoffset.getValue(), Alignment.CENTER, false, f1_2011Font.getFont(), isFontAntiAliased(), fontColor3.getColor() );
            
            top += rowHeight;
        }
        //Scan Manufacturer Folder
        File dir = new File(gameData.getFileSystem().getImagesFolder().toString() + "/prunn/V8SC_2013/Manufacturer");

        String[] children = dir.list();
        NumOfPNG = 0;
        listPNG = new String[children.length];
        
        for (int i=0; i < children.length; i++) 
        {
            // Get filename of file or directory
            String filename = children[i];
            
            if(filename.substring( filename.length()-4 ).toUpperCase().equals( ".PNG" ) )
            {
                //log(filename.substring( 0, filename.length()-4 ));
                listPNG[NumOfPNG] = filename.substring( 0, filename.length()-4 );
                NumOfPNG++;
            }    
        }
        //end of scan
    }
    
    @Override
    protected Boolean updateVisibility( LiveGameData gameData, boolean isEditorMode )
    {
        super.updateVisibility( gameData, isEditorMode );
        ScoringInfo scoringInfo = gameData.getScoringInfo();
        int drawncars = Math.min( scoringInfo.getNumVehicles(), numVeh.getValue() );
        initValues();
        
        for(int i=0;i < drawncars;i++)
        { 
            VehicleScoringInfo vsi = scoringInfo.getVehicleScoringInfo( i );
            
            if(vsi != null)
            {
                positions[i].update( vsi.getPlace( false ) );
                driverNames[i].update( vsi.getDriverName() );
                
                
                if(scoringInfo.getSessionType() != SessionType.RACE1)
                    gaps[i].update(vsi.getBestLapTime());
                else
                    gaps[i].update(vsi.getNumPitstopsMade());
                    
                if(driverNames[i].hasChanged())    
                    forceCompleteRedraw( true );
                
            }
        }
        return true;
    }
    @Override
    protected void drawBackground( LiveGameData gameData, boolean isEditorMode, TextureImage2D texture, int offsetX, int offsetY, int width, int height, boolean isRoot )
    {
        super.drawBackground( gameData, isEditorMode, texture, offsetX, offsetY, width, height, isRoot );
        
        ScoringInfo scoringInfo = gameData.getScoringInfo();
        
        int maxNumItems = numVeh.getValue();
        int drawncars = Math.min( scoringInfo.getNumVehicles(), maxNumItems );
        int rowHeight = height / (maxNumItems + 2);
        String team;
        
        texture.clear( imgTrack.getTexture(), offsetX, offsetY, false, null );
        
        for(int i=0;i < drawncars;i++)
        {
            texture.clear( imgFord.getTexture(), offsetX, offsetY+rowHeight*(i+2), false, null );
            if(isEditorMode)
                team = "holden";
            else if(scoringInfo.getVehicleScoringInfo( i ).getVehicleInfo() != null)
                team = scoringInfo.getVehicleScoringInfo( i ).getVehicleInfo().getManufacturer();
            else
                team = scoringInfo.getVehicleScoringInfo( i ).getVehicleClass();

            for(int j=0; j < NumOfPNG; j++)
            {
                if(team.length() >= listPNG[j].length() && team.substring( 0, listPNG[j].length() ).toUpperCase().equals( listPNG[j].toUpperCase() )) 
                {
                    imgManufacturer.setValue("prunn/V8SC_2013/Manufacturer/" + listPNG[j] + ".png");
                    texManufacturer = imgManufacturer.getImage().getScaledTextureImage( width*15/200, rowHeight, texManufacturer, isEditorMode );
                    texture.drawImage( texManufacturer, offsetX + width*10/100, offsetY+rowHeight*(i+2), true, null );
                    break;
                }
            }
        }
        
    }
    
    @Override
    protected void drawWidget( Clock clock, boolean needsCompleteRedraw, LiveGameData gameData, boolean isEditorMode, TextureImage2D texture, int offsetX, int offsetY, int width, int height )
    {
        ScoringInfo scoringInfo = gameData.getScoringInfo();
        int drawncars = Math.min( scoringInfo.getNumVehicles(), numVeh.getValue() );
        String SessionName;
        String posTH; 
        //one time for leader
        
        if ( needsCompleteRedraw || clock.c())
        {
            switch(scoringInfo.getSessionType())
            {
                case RACE1: case RACE2: case RACE3: case RACE4:
                    SessionName = "Race";
                    break;
                case QUALIFYING1: case QUALIFYING2: case QUALIFYING3:  case QUALIFYING4:
                    SessionName = "Qualifying";
                    break;
                case PRACTICE1:
                    SessionName = "Practice 1";
                    break;
                case PRACTICE2:
                    SessionName = "Practice 2";
                    break;
                case PRACTICE3:
                    SessionName = "Practice 3";
                    break;
                case PRACTICE4:
                    SessionName = "Practice 4";
                    break;
                case TEST_DAY:
                    SessionName = "Test";
                    break;
                case WARMUP:
                    SessionName = "Warmup";
                    break;
                default:
                    SessionName = "";
                    break;
                        
            }
            //" Session Classification"
            dsTrack.draw( offsetX, offsetY, SessionName + "  |  " + gameData.getTrackInfo().getTrackName(), texture);
            
            dsPos[0].draw( offsetX, offsetY, positions[0].getValueAsString() + "ST", texture );
            dsName[0].draw( offsetX, offsetY, driverNames[0].getValue(), texture );
            
            if(scoringInfo.getSessionType() == SessionType.RACE1 )
            {
                String stops = ( scoringInfo.getLeadersVehicleScoringInfo().getNumPitstopsMade() > 1 ) ? " Stops" : " Stop";
                dsTime[0].draw( offsetX, offsetY, scoringInfo.getLeadersVehicleScoringInfo().getNumPitstopsMade() + stops, texture);
            }
            else
                if(gaps[0].isValid())
                    dsTime[0].draw( offsetX, offsetY, TimingUtil.getTimeAsLaptimeString(gaps[0].getValue() ) , texture);
                else
                    dsTime[0].draw( offsetX, offsetY, "-:--.---", texture);
        
        
            // the other guys
            for(int i=1;i < drawncars;i++)
            { 
                if ( needsCompleteRedraw || clock.c() )
                {
                    if(positions[i].getValue() == 2)
                        posTH = "ND";
                    else if(positions[i].getValue() == 3)
                        posTH = "RD";
                    else
                        posTH = "TH";
                    
                    dsPos[i].draw( offsetX, offsetY, positions[i].getValueAsString() + posTH, texture );
                    dsName[i].draw( offsetX, offsetY,driverNames[i].getValue() , texture );  
                    if(scoringInfo.getVehicleScoringInfo( i ).getFinishStatus() == FinishStatus.DQ)
                        dsTime[i].draw( offsetX, offsetY, "DQ", texture);
                    else
                        if(scoringInfo.getSessionType() == SessionType.RACE1 )
                        {
                            if(scoringInfo.getVehicleScoringInfo( i ).getFinishStatus() == FinishStatus.DNF)
                                dsTime[i].draw( offsetX, offsetY, "DNF", texture); 
                            else
                            {
                                String stops = ( scoringInfo.getVehicleScoringInfo( i ).getNumPitstopsMade() > 1 ) ? " Stops" : " Stop";
                                dsTime[i].draw( offsetX, offsetY, scoringInfo.getVehicleScoringInfo( i ).getNumPitstopsMade() + stops, texture);
                            }
                        }
                        else
                            if(!gaps[i].isValid())
                                dsTime[i].draw( offsetX, offsetY, "-:--.---", texture);
                            else
                                if(AbsTimes.getValue() || !gaps[0].isValid())
                                   dsTime[i].draw( offsetX, offsetY, TimingUtil.getTimeAsLaptimeString(gaps[i].getValue() ), texture);
                                else
                                    dsTime[i].draw( offsetX, offsetY,"+ " + TimingUtil.getTimeAsLaptimeString(Math.abs( gaps[i].getValue() - gaps[0].getValue() )) , texture);
                 }
                
            }
        }
    }
    
    
    @Override
    public void saveProperties( PropertyWriter writer ) throws IOException
    {
        super.saveProperties( writer );
        
        writer.writeProperty( f1_2011Font, "" );
        writer.writeProperty( v8sc_huge_font, "" );
        writer.writeProperty( fontColor3, "" );
        writer.writeProperty( fontColor2, "" );
        writer.writeProperty( fontColorGrey, "" );
        writer.writeProperty( numVeh, "" );
        writer.writeProperty( AbsTimes, "" );
        writer.writeProperty( fontyoffset, "" );
        writer.writeProperty( fontxposoffset, "" );
        writer.writeProperty( fontxnameoffset, "" );
        writer.writeProperty( fontxtimeoffset, "" );
    }
    
    @Override
    public void loadProperty( PropertyLoader loader )
    {
        super.loadProperty( loader );
        
        if ( loader.loadProperty( f1_2011Font ) );
        else if ( loader.loadProperty( v8sc_huge_font ) );
        else if ( loader.loadProperty( fontColor3 ) );
        else if ( loader.loadProperty( fontColor2 ) );
        else if ( loader.loadProperty( fontColorGrey ) );
        else if ( loader.loadProperty( numVeh ) );
        else if ( loader.loadProperty( AbsTimes ) );
        else if ( loader.loadProperty( fontyoffset ) );
        else if ( loader.loadProperty( fontxposoffset ) );
        else if ( loader.loadProperty( fontxnameoffset ) );
        else if ( loader.loadProperty( fontxtimeoffset ) );
    }
    
    @Override
    protected void addFontPropertiesToContainer( PropertiesContainer propsCont, boolean forceAll )
    {
        propsCont.addGroup( "Colors and Fonts" );
        
        super.addFontPropertiesToContainer( propsCont, forceAll );
        propsCont.addProperty( f1_2011Font );
        propsCont.addProperty( v8sc_huge_font );
        propsCont.addProperty( fontColor3 );
        propsCont.addProperty( fontColor2 );
        propsCont.addProperty( fontColorGrey );
    }
    
    @Override
    public void getProperties( PropertiesContainer propsCont, boolean forceAll )
    {
        super.getProperties( propsCont, forceAll );
        
        propsCont.addGroup( "Specific" );
        
        propsCont.addProperty( numVeh );
        propsCont.addProperty( AbsTimes );
        propsCont.addGroup( "Font Displacement" );
        propsCont.addProperty( fontyoffset );
        propsCont.addProperty( fontxposoffset );
        propsCont.addProperty( fontxnameoffset );
        propsCont.addProperty( fontxtimeoffset );
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
    
    public ResultMonitorWidget()
    {
        super( PrunnWidgetSetv8SC_2013.INSTANCE, PrunnWidgetSetv8SC_2013.WIDGET_PACKAGE_V8, 66.4f, 46.5f );
        
        getBackgroundProperty().setColorValue( "#00000000" );
        getFontProperty().setFont( PrunnWidgetSetv8SC_2013.V8_FONT_NAME );
        getFontColorProperty().setColor( PrunnWidgetSetv8SC_2013.FONT_COLOR1_NAME );
    }
}
