/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maoguerra007.monitoreo;

import com.sun.jna.platform.win32.Dxva2;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import maoguerra007.monitoreo.util.Util;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

/**
 *
 * @author Mauricio Guerra Cubillos <maoguerra007 at gmail.com>
 */
public class Inicio {

    private static int conta;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        byte ambiente = Util.ambientes[0];
        new NativeDiscovery().discover();
        System.out.println("Inicializar" + System.getProperty("java.class.path"));
        if (ambiente == 00) {
            buscarMonitores();
            System.out.println("conta = " + conta);
            java.awt.EventQueue.invokeLater(() -> {
                new Video();
            });
        }
        System.out.println("Otro ambiente");
    }

    private static void buscarMonitores() {

        User32.INSTANCE.EnumDisplayMonitors(null, null, new WinUser.MONITORENUMPROC() {
            int cont;

            @Override
            public int apply(WinUser.HMONITOR hmntr, WinDef.HDC hdc, WinDef.RECT rect, WinDef.LPARAM lparam) {
                buscar(hmntr);
                return 0;
            }
        }, new WinDef.LPARAM(0));
    }

    private static void buscar(WinUser.HMONITOR hmntr) {
        WinDef.DWORDByReference pdwNumberOfPhysicalMonitors = new WinDef.DWORDByReference();
        Dxva2.INSTANCE.GetNumberOfPhysicalMonitorsFromHMONITOR(hmntr, pdwNumberOfPhysicalMonitors);
        conta = pdwNumberOfPhysicalMonitors.getValue().intValue();
    }

}
