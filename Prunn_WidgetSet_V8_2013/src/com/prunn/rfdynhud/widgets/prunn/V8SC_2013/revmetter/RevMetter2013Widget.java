package com.prunn.rfdynhud.widgets.prunn.V8SC_2013.revmetter;

import java.awt.Font;
import java.io.IOException;

import com.prunn.rfdynhud.widgets.prunn._util.PrunnWidgetSetv8SC_2013;
import net.ctdp.rfdynhud.gamedata.LiveGameData;
import net.ctdp.rfdynhud.gamedata.ProfileInfo.MeasurementUnits;
import net.ctdp.rfdynhud.gamedata.ScoringInfo;
import net.ctdp.rfdynhud.gamedata.TelemetryData;
import net.ctdp.rfdynhud.gamedata.VehicleScoringInfo;
import net.ctdp.rfdynhud.properties.BooleanProperty;
import net.ctdp.rfdynhud.properties.ColorProperty;
import net.ctdp.rfdynhud.properties.FontProperty;
import net.ctdp.rfdynhud.properties.ImageProperty;
import net.ctdp.rfdynhud.properties.ImagePropertyWithTexture;
import net.ctdp.rfdynhud.properties.PropertiesContainer;
import net.ctdp.rfdynhud.properties.PropertyLoader;
import net.ctdp.rfdynhud.render.DrawnString;
import net.ctdp.rfdynhud.render.DrawnStringFactory;
import net.ctdp.rfdynhud.render.ImageTemplate;
import net.ctdp.rfdynhud.render.TextureImage2D;
import net.ctdp.rfdynhud.render.TransformableTexture;
import net.ctdp.rfdynhud.render.DrawnString.Alignment;
import net.ctdp.rfdynhud.util.FontUtils;
import net.ctdp.rfdynhud.util.PropertyWriter;
import net.ctdp.rfdynhud.util.SubTextureCollector;
import net.ctdp.rfdynhud.valuemanagers.Clock;
import net.ctdp.rfdynhud.values.IntValue;
import net.ctdp.rfdynhud.widgets.base.widget.Widget;



/**
 * @author Prunn
 * copyright@Prunn2011
 * 
 */


public class RevMetter2013Widget extends Widget
{
    private DrawnString dsSpeed = null;
    private DrawnString dsSpeedUnit = null;
    private DrawnString dsGear = null;
    private DrawnString dsBrake = null;
    private DrawnString dsGearAvail1 = null;
    private DrawnString dsGearAvail2 = null;
    private DrawnString dsGearAvail3 = null;
    private DrawnString dsGearAvail4 = null;
    private DrawnString dsRPM1 = null;
    private DrawnString dsRPM2 = null;
    private DrawnString dsRPM3 = null;
    private DrawnString dsRPM4 = null;
    private DrawnString dsRPM5 = null;
    private DrawnString dsRPM6 = null;
    private DrawnString dsRPM7 = null;
    private DrawnString dsRPM8 = null;
    
    private final ImageProperty ImgBrake = new ImageProperty("ImgBrake", null, "prunn/V8SC_2013/revmetter/brake.png", false, false);
    private final ImageProperty ImgBrakeOff = new ImageProperty("ImgBrake", null, "prunn/V8SC_2013/revmetter/brakeoff.png", false, false);
    private final ImagePropertyWithTexture ImgRevmetter = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/background.png", false, false );  
    private final ImagePropertyWithTexture Img250 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/250.png", false, false ); 
    private final ImagePropertyWithTexture Img500 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/500.png", false, false );  
    private final ImagePropertyWithTexture Img1000 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/1000.png", false, false ); 
    private final ImagePropertyWithTexture Img1250 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/1250.png", false, false );  
    private final ImagePropertyWithTexture Img1500 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/1500.png", false, false );   
    private final ImagePropertyWithTexture Img1750 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/1750.png", false, false ); 
    private final ImagePropertyWithTexture Img2000 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/2000.png", false, false ); 
    private final ImagePropertyWithTexture Img2250 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/2250.png", false, false );   
    private final ImagePropertyWithTexture Img2500 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/2500.png", false, false );   
    private final ImagePropertyWithTexture Img2750 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/2750.png", false, false ); 
    private final ImagePropertyWithTexture Img3000 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/3000.png", false, false ); 
    private final ImagePropertyWithTexture Img3250 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/3250.png", false, false );  
    private final ImagePropertyWithTexture Img3500 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/3500.png", false, false );   
    private final ImagePropertyWithTexture Img3750 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/3750.png", false, false );  
    private final ImagePropertyWithTexture Img4000 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/4000.png", false, false ); 
    private final ImagePropertyWithTexture Img4250 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/4250.png", false, false ); 
    private final ImagePropertyWithTexture Img4500 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/4500.png", false, false );  
    private final ImagePropertyWithTexture Img4750 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/4750.png", false, false );  
    private final ImagePropertyWithTexture Img5000 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/5000.png", false, false );
    private final ImagePropertyWithTexture Img5250 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/5250.png", false, false ); 
    private final ImagePropertyWithTexture Img5500 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/5500.png", false, false );  
    private final ImagePropertyWithTexture Img5750 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/5750.png", false, false );  
    private final ImagePropertyWithTexture Img6000 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/6000.png", false, false ); 
    private final ImagePropertyWithTexture Img6250 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/6250.png", false, false ); 
    private final ImagePropertyWithTexture Img6500 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/6500.png", false, false ); 
    private final ImagePropertyWithTexture Img6750 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/6750.png", false, false );  
    private final ImagePropertyWithTexture Img7000 = new ImagePropertyWithTexture( "image", null, "prunn/V8SC_2013/revmetter/7000.png", false, false );
    private TextureImage2D texRev = null;
    
    protected final FontProperty speedUnitFont = new FontProperty("speed Unit Font", "speedUnitFont");
    protected final FontProperty speedF11Font = new FontProperty("speed Font", "speedF11Font");
    protected final FontProperty gearF11Font = new FontProperty("gear Font", "gearF11Font");
    protected final FontProperty gearF11FontSide = new FontProperty("gear Font 2", "gearF11FontSide");
    protected final FontProperty rpmFont = new FontProperty("RPM Font", "rpmFont");
    protected final FontProperty TBFont = new FontProperty("Brake Font", "TBFont");
    private final ColorProperty speedUnitFontColor = new ColorProperty("speedUnitFontColor", "speedUnitFontColor");
    private final ColorProperty speedFontColor = new ColorProperty("speedFontColor", "speedFontColor");
    private final ColorProperty GearFontColor = new ColorProperty("Gear Font Color", "GearFontColor");
    private final ColorProperty GearFontColorL1 = new ColorProperty("GearFontColorL1", "GearFontColorL1");
    private final ColorProperty GearFontColorL2 = new ColorProperty("GearFontColorL2", "GearFontColorL2");
    private final ColorProperty rpmFontColor = new ColorProperty("rpmFontColor", "rpmFontColor");
    private IntValue cSpeed = new IntValue();
    private IntValue cGear = new IntValue();
    private TransformableTexture texBrake1;
    private TransformableTexture texBrake2;
    private boolean BrakeDirty = false;
    private final ColorProperty TBFontColor = new ColorProperty("TBFont Color", "TBFontColor");
    private IntValue tenthOfSec = new IntValue();
    protected final BooleanProperty useMaxRevLimit = new BooleanProperty("useMaxRevLimit", "useMaxRevLimit", true);
    
    
    public String getDefaultNamedColorValue(String name)
    {
        if(name.equals("StandardBackground"))
            return "#00000000";
        if(name.equals("StandardFontColor"))
            return "#FFFFFF";
        if(name.equals("speedFontColor"))
            return "#F6F6F6";
        if(name.equals("GearFontColor"))
            return "#F3E900";
        if(name.equals("GearFontColorL1"))
            return "#9E9F2F";
        if(name.equals("GearFontColorL2"))
            return "#00000000";
        if(name.equals("TBFontColor"))
            return "#FFFFFFD7";
        if(name.equals("rpmFontColor"))
            return "#BEBEBEBE";
        if(name.equals("speedUnitFontColor"))
            return "#FFFFFFA7";

        return null;
    }
    
    @Override
    public String getDefaultNamedFontValue(String name)
    {
        if(name.equals("StandardFont"))
            return FontUtils.getFontString("Dialog", 1, 16, true, true);
        if(name.equals("speedF11Font"))
            return FontUtils.getFontString("Folio Std Bold Condensed", Font.PLAIN, 54, true, true);
        if(name.equals("speedUnitFont"))
            return FontUtils.getFontString("Folio Std Bold Condensed", 1, 21, true, true);
        if(name.equals("gearF11Font"))
            return FontUtils.getFontString("Folio Std Bold Condensed", 1, 52, true, true);
        if(name.equals("gearF11FontSide"))
            return FontUtils.getFontString("Folio Std Bold Condensed", 1, 32, true, true);
        if(name.equals("TBFont"))
            return FontUtils.getFontString("Folio Std Bold Condensed", 1, 16, true, true);
        if(name.equals("rpmFont"))
            return FontUtils.getFontString("Folio Std Bold Condensed", 1, 20, true, true);
        
        return null;
    }
    
    protected Boolean onVehicleControlChanged(VehicleScoringInfo viewedVSI, LiveGameData gameData, boolean isEditorMode)
    {
        super.onVehicleControlChanged(viewedVSI, gameData, isEditorMode);
        
        return viewedVSI.isPlayer();
    }
    
    
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
        int wTB = widgetInnerWidth;
        int hTB = widgetInnerHeight;
        //"BRAKE"
        if(texBrake2 == null || BrakeDirty || (isEditorMode && (texBrake1.getWidth() != wTB || texBrake1.getHeight() != hTB) ))
        {
            texBrake1 = TransformableTexture.getOrCreate(wTB, hTB, true, texBrake1, isEditorMode);
            texBrake2 = TransformableTexture.getOrCreate(wTB, hTB, true, texBrake2, isEditorMode);
            ImageTemplate itOff = ImgBrake.getImage();
            ImageTemplate it = ImgBrakeOff.getImage();
            it.drawScaled(0, 0, it.getBaseWidth(), it.getBaseHeight() , 0, 0, wTB, hTB, texBrake1.getTexture(), true);
            itOff.drawScaled(0, 0, it.getBaseWidth(), it.getBaseHeight() , 0, 0, wTB, hTB, texBrake2.getTexture(), true);
            texBrake1.setLocalZIndex(503);
            texBrake2.setLocalZIndex(502);
            BrakeDirty = false;
        }
        collector.add(texBrake1);
        collector.add(texBrake2);
    }
    
    @Override
    protected void initialize( LiveGameData gameData, boolean isEditorMode, DrawnStringFactory drawnStringFactory, TextureImage2D texture, int width, int height )
    {
        int fhSpeed = TextureImage2D.getStringHeight( "09gy", speedF11Font );
        int fhSpeedUnit = TextureImage2D.getStringHeight( "09gy", speedUnitFont );
        int fhGear = TextureImage2D.getStringHeight( "09gy", gearF11Font );
        int fhGear2 = TextureImage2D.getStringHeight( "09gy", gearF11FontSide );
        int fhRPM = TextureImage2D.getStringHeight( "09gy", gearF11FontSide );
        
        dsSpeed = drawnStringFactory.newDrawnString( "dsSpeed", width*49/100, height*47/100 - fhSpeed/2, Alignment.CENTER, false, speedF11Font.getFont(), isFontAntiAliased(), speedFontColor.getColor() );
        dsSpeedUnit = drawnStringFactory.newDrawnString( "dsSpeedUnit", width*49/100, height*32/100 - fhSpeedUnit/2, Alignment.CENTER, false, speedUnitFont.getFont(), isFontAntiAliased(), speedUnitFontColor.getColor() );
        dsGear = drawnStringFactory.newDrawnString( "dsGear", width*49/100, height*67/100 - fhGear/2, Alignment.CENTER, false, gearF11Font.getFont(), isFontAntiAliased(), GearFontColor.getColor() );
        dsGearAvail1 = drawnStringFactory.newDrawnString( "dsGearAvail1", width*32/100, height*65/100 - fhGear2/2, Alignment.CENTER, false, gearF11FontSide.getFont(), isFontAntiAliased(), GearFontColorL2.getColor() );
        dsGearAvail2 = drawnStringFactory.newDrawnString( "dsGearAvail2", width*38/100, height*65/100 - fhGear2/2, Alignment.CENTER, false, gearF11FontSide.getFont(), isFontAntiAliased(), GearFontColorL1.getColor() );
        dsGearAvail3 = drawnStringFactory.newDrawnString( "dsGearAvail3", width*60/100, height*65/100 - fhGear2/2, Alignment.CENTER, false, gearF11FontSide.getFont(), isFontAntiAliased(), GearFontColorL1.getColor() );
        dsGearAvail4 = drawnStringFactory.newDrawnString( "dsGearAvail4", width*65/100, height*65/100 - fhGear2/2, Alignment.CENTER, false, gearF11FontSide.getFont(), isFontAntiAliased(), GearFontColorL2.getColor() );
        
        dsRPM1 = drawnStringFactory.newDrawnString( "dsRPM1", width*28/100, height*78/100 - fhRPM/2, Alignment.CENTER, false, rpmFont.getFont(), isFontAntiAliased(), rpmFontColor.getColor() );
        dsRPM2 = drawnStringFactory.newDrawnString( "dsRPM2", width*18/100, height*62/100 - fhRPM/2, Alignment.CENTER, false, rpmFont.getFont(), isFontAntiAliased(), rpmFontColor.getColor() );
        dsRPM3 = drawnStringFactory.newDrawnString( "dsRPM3", width*21/100, height*40/100 - fhRPM/2, Alignment.CENTER, false, rpmFont.getFont(), isFontAntiAliased(), rpmFontColor.getColor() );
        dsRPM4 = drawnStringFactory.newDrawnString( "dsRPM4", width*34/100, height*26/100 - fhRPM/2, Alignment.CENTER, false, rpmFont.getFont(), isFontAntiAliased(), rpmFontColor.getColor() );
        dsRPM5 = drawnStringFactory.newDrawnString( "dsRPM5", width*54/100, height*23/100 - fhRPM/2, Alignment.CENTER, false, rpmFont.getFont(), isFontAntiAliased(), rpmFontColor.getColor() );
        dsRPM6 = drawnStringFactory.newDrawnString( "dsRPM6", width*72/100, height*31/100 - fhRPM/2, Alignment.CENTER, false, rpmFont.getFont(), isFontAntiAliased(), rpmFontColor.getColor() );
        dsRPM7 = drawnStringFactory.newDrawnString( "dsRPM7", width*81/100, height*47/100 - fhRPM/2, Alignment.CENTER, false, rpmFont.getFont(), isFontAntiAliased(), rpmFontColor.getColor() );
        dsRPM8 = drawnStringFactory.newDrawnString( "dsRPM8", width*79/100, height*68/100 - fhRPM/2, Alignment.CENTER, false, rpmFont.getFont(), isFontAntiAliased(), rpmFontColor.getColor() );
        dsBrake = drawnStringFactory.newDrawnString( "dsBrake", width/2, height*1/100, Alignment.CENTER, false, TBFont.getFont(), isFontAntiAliased(), TBFontColor.getColor() );
        
        ImgRevmetter.updateSize( width, height, isEditorMode );
        
    }
    protected Boolean updateVisibility(LiveGameData gameData, boolean isEditorMode)
    {
        
        super.updateVisibility(gameData, isEditorMode);
        ScoringInfo scoringInfo = gameData.getScoringInfo();
        
        if(!isEditorMode)
            forceCompleteRedraw(true);
        if(scoringInfo.getViewedVehicleScoringInfo().isPlayer())
        {
           return true; 
        }
        return false;
         
    }
    
    
    
    
    @Override
    protected void drawBackground( LiveGameData gameData, boolean isEditorMode, TextureImage2D texture, int offsetX, int offsetY, int width, int height, boolean isRoot )
    {
        super.drawBackground( gameData, isEditorMode, texture, offsetX, offsetY, width, height, isRoot );
        texture.drawImage( ImgRevmetter.getTexture(), offsetX, offsetY, true, null );
        
    }
    
    @Override
    protected void drawWidget( Clock clock, boolean needsCompleteRedraw, LiveGameData gameData, boolean isEditorMode, TextureImage2D texture, int offsetX, int offsetY, int width, int height )
    {
        TelemetryData telemData = gameData.getTelemetryData();
        float uBrake = isEditorMode ? 0.6F : telemData.getUnfilteredBrake();
        int wT = texBrake1.getHeight();
        int brake = (int)((float)wT * uBrake);
        float uRPM;
        if ( useMaxRevLimit.getBooleanValue() )
            uRPM = isEditorMode ? 0.5F : telemData.getEngineRPM() / gameData.getPhysics().getEngine().getRevLimitRange().getMaxValue();
        else
            uRPM = isEditorMode ? 0.5F : telemData.getEngineRPM() / gameData.getSetup().getEngine().getRevLimit();
        
        if(uBrake>0.2)
            brake = texBrake1.getHeight();
        texBrake1.setClipRect(0, 0, texBrake1.getWidth(), texBrake1.getHeight() - brake, true);
        
        
        cGear.update( telemData.getCurrentGear() );
        
        tenthOfSec.update((int)(gameData.getScoringInfo().getSessionNanos() / 10000000f));
        if(gameData.getProfileInfo().getMeasurementUnits() == MeasurementUnits.METRIC)
            cSpeed.update( (int)telemData.getScalarVelocityKPH() );
        else
            cSpeed.update( (int)telemData.getScalarVelocityMPH() );
        
        
        
            
        if(needsCompleteRedraw || cSpeed.hasChanged())
        {
            dsSpeed.draw( offsetX, offsetY, String.valueOf( cSpeed.getValue() ), speedFontColor.getColor(), texture );
            
            if(gameData.getProfileInfo().getMeasurementUnits() == MeasurementUnits.METRIC)
                dsSpeedUnit.draw( offsetX, offsetY, "km/h", texture );
            else
                dsSpeedUnit.draw( offsetX, offsetY, "mph", texture );
             
        }
        
        if(needsCompleteRedraw || ( clock.c() && cGear.hasChanged() ))
        {
            if(cGear.getValue() == -1)
                dsGear.draw( offsetX, offsetY, "R", texture );
            else
                if(cGear.getValue() == 0)
                    dsGear.draw( offsetX, offsetY, "N", texture );
                else
                    dsGear.draw( offsetX, offsetY, String.valueOf( cGear.getValue() ), texture );
                    
            if(cGear.getValue() == 1)
                dsGearAvail1.draw( offsetX, offsetY, "R", texture );
            else
                if(cGear.getValue() == 2)
                    dsGearAvail1.draw( offsetX, offsetY, "N", texture );
                else
                    if(cGear.getValue() > 0)
                        dsGearAvail1.draw( offsetX, offsetY, String.valueOf( cGear.getValue() - 2 ), texture );
                    else
                        dsGearAvail1.draw( offsetX, offsetY, "", texture );
                
            if(cGear.getValue() == 0)
                dsGearAvail2.draw( offsetX, offsetY, "R", texture );
            else
                if(cGear.getValue() == 1)
                    dsGearAvail2.draw( offsetX, offsetY, "N", texture );
                else
                    if(cGear.getValue() > 1)
                        dsGearAvail2.draw( offsetX, offsetY, String.valueOf( cGear.getValue() - 1 ), texture );
                    else
                        dsGearAvail2.draw( offsetX, offsetY, "", texture );
            if(cGear.getValue() == -1)
                dsGearAvail3.draw( offsetX, offsetY, "N", texture );
            else
                if(cGear.getValue() < gameData.getPhysics().getNumForwardGears() )//7
                    dsGearAvail3.draw( offsetX, offsetY, String.valueOf( cGear.getValue() + 1 ), texture );
                else
                    dsGearAvail3.draw( offsetX, offsetY, "", texture );
                
            if(cGear.getValue() < gameData.getPhysics().getNumForwardGears() - 1 )//6
                dsGearAvail4.draw( offsetX, offsetY, String.valueOf( cGear.getValue() + 2 ), texture );
            else
                dsGearAvail4.draw( offsetX, offsetY, "", texture );
         }
        //37
        float maxURPM = 0.990f;
        if(uRPM>=maxURPM)
            texRev = Img7000.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*27/28)
            texRev = Img6750.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*26/28)
            texRev = Img6500.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*25/28)
            texRev = Img6250.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*24/28)
            texRev = Img6000.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*23/28)
            texRev = Img5750.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*22/28)
            texRev = Img5500.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*21/28)
            texRev = Img5250.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*20/28)
            texRev = Img5000.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*19/28)
            texRev = Img4750.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*18/28)
            texRev = Img4500.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*17/28)
            texRev = Img4250.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*16/28)
            texRev = Img4000.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*15/28)
            texRev = Img3750.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM/2)
            texRev = Img3500.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*13/28)
            texRev = Img3250.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*12/28)
            texRev = Img3000.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*11/28)
            texRev = Img2750.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*10/28)
            texRev = Img2500.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*9/28)
            texRev = Img2250.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*8/28)
            texRev = Img2000.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*7/28)
            texRev = Img1750.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*6/28)
            texRev = Img1500.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*5/28)
            texRev = Img1250.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*4/28)
            texRev = Img1000.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else if(uRPM>=maxURPM*3/28)
            texRev = Img500.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        else //if(uRPM>0.37)
            texRev = Img250.getImage().getScaledTextureImage( width, height, texRev, isEditorMode );
        
        texture.drawImage( texRev, offsetX, offsetY, true, null );
        
        
        
        
        
        if(needsCompleteRedraw || isEditorMode)
        {
            dsBrake.draw( offsetX, offsetY, "BRAKE", texture );
            
            float maxRPM; //telemData.getEngineMaxRPM()
            if ( useMaxRevLimit.getBooleanValue() )
                maxRPM = gameData.getPhysics().getEngine().getRevLimitRange().getMaxValue();
            else
                maxRPM = gameData.getSetup().getEngine().getRevLimit();
            
            dsRPM1.draw( offsetX, offsetY, String.valueOf((int)maxRPM*0/18000), texture );
            dsRPM2.draw( offsetX, offsetY, String.valueOf((int)maxRPM*4/18000), texture );
            dsRPM3.draw( offsetX, offsetY, String.valueOf((int)maxRPM*6/18000), texture );
            dsRPM4.draw( offsetX, offsetY, String.valueOf((int)maxRPM*8/18000), texture );
            dsRPM5.draw( offsetX, offsetY, String.valueOf((int)maxRPM*10/18000), texture );
            dsRPM6.draw( offsetX, offsetY, String.valueOf((int)maxRPM*12/18000), texture );
            dsRPM7.draw( offsetX, offsetY, String.valueOf((int)maxRPM*16/18000), texture );
            dsRPM8.draw( offsetX, offsetY, String.valueOf((int)maxRPM/1000), texture );
            
        }
        
        
    }
    
    
    @Override
    public void saveProperties( PropertyWriter writer ) throws IOException
    {
        super.saveProperties( writer );
        writer.writeProperty( speedF11Font, "" );
        writer.writeProperty( speedFontColor, "" );
        writer.writeProperty( speedUnitFont, "" );
        writer.writeProperty( speedUnitFontColor, "" );
        writer.writeProperty( gearF11Font, "" );
        writer.writeProperty( gearF11FontSide, "" );
        writer.writeProperty( GearFontColor, "" );
        writer.writeProperty( GearFontColorL1, "" );
        writer.writeProperty( GearFontColorL2, "" );
        writer.writeProperty( TBFontColor, "" );
        writer.writeProperty( TBFont, "" );
        writer.writeProperty( rpmFont, "" );
        writer.writeProperty( rpmFontColor, "" );
        writer.writeProperty( useMaxRevLimit, "" );
        
    }
    
    @Override
    public void loadProperty( PropertyLoader loader )
    {
        super.loadProperty( loader );
        if ( loader.loadProperty( speedF11Font ) );
        else if ( loader.loadProperty( speedFontColor ) );
        else if ( loader.loadProperty( speedUnitFont ) );
        else if ( loader.loadProperty( speedUnitFontColor ) );
        else if ( loader.loadProperty( gearF11Font ) );
        else if ( loader.loadProperty( gearF11FontSide ) );
        else if ( loader.loadProperty( GearFontColor ) );
        else if ( loader.loadProperty( GearFontColorL1 ) );
        else if ( loader.loadProperty( GearFontColorL2 ) );
        else if ( loader.loadProperty( TBFontColor ) );
        else if ( loader.loadProperty( rpmFont ) );
        else if ( loader.loadProperty( TBFont ) );
        else if ( loader.loadProperty( rpmFontColor ) );
        else if ( loader.loadProperty( useMaxRevLimit ) );
        
        
    }
    
    @Override
    public void getProperties( PropertiesContainer propsCont, boolean forceAll )
    {
        super.getProperties( propsCont, forceAll );
        
        propsCont.addGroup( "Misc" );
        propsCont.addProperty( speedF11Font );
        propsCont.addProperty( speedFontColor );
        propsCont.addProperty( speedUnitFont );
        propsCont.addProperty( speedUnitFontColor );
        propsCont.addProperty( gearF11Font );
        propsCont.addProperty( gearF11FontSide );
        propsCont.addProperty( GearFontColor );
        propsCont.addProperty( GearFontColorL1 );
        propsCont.addProperty( GearFontColorL2 );
        propsCont.addProperty( TBFontColor );
        propsCont.addProperty( rpmFont );
        propsCont.addProperty( TBFont );
        propsCont.addProperty( rpmFontColor );
        propsCont.addProperty( useMaxRevLimit );
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
    
    public RevMetter2013Widget()
    {
        super( PrunnWidgetSetv8SC_2013.INSTANCE, PrunnWidgetSetv8SC_2013.WIDGET_PACKAGE_V8, 23.4f, 26.7f );
        getBackgroundProperty().setColorValue( "#00000000" );
        
    }
    
}
