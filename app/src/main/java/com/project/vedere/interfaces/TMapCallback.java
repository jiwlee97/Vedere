package com.project.vedere.interfaces;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapPOIItem;

import org.w3c.dom.Document;

import java.util.ArrayList;


public interface TMapCallback extends TMapData.FindPathDataAllListenerCallback, TMapData.FindAllPOIListenerCallback, TMapGpsManager.onLocationChangedCallback  {

}
