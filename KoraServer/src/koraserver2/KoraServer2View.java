/*
 * KoraServer2View.java
 */

package koraserver2;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.logging.LogManager;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;

import org.gskbyte.kora.server.clients.deviceList.DeviceListServant;
import org.gskbyte.kora.server.clients.events.DeviceChangeEvent;
import org.gskbyte.kora.server.clients.events.ClientChangeEventListener;
import org.gskbyte.kora.server.clients.events.ClientQueryEventListener;
import org.gskbyte.kora.server.devices.Device;
import org.gskbyte.kora.server.devices.DeviceManager;
import org.gskbyte.kora.server.devices.knx.KNXBooleanDevice;
import org.gskbyte.kora.server.devices.knx.KNXDevice;
import org.gskbyte.kora.server.devices.knx.KNXScalingDevice;
import org.gskbyte.kora.server.util.Log;
import org.ugr.bluerose.devices.TcpCompatibleDevice;
import org.ugr.bluerose.events.Event;
import org.ugr.bluerose.events.Value;

import tuwien.auto.calimero.knxnetip.Discoverer;
import tuwien.auto.calimero.log.LogWriter;
/**
 * The application's main frame.
 */
public class KoraServer2View extends FrameView implements WindowListener
{
    private boolean mConnected = false;
    private DeviceManager mDeviceManager;

    public KoraServer2View(SingleFrameApplication app) {
        super(app);

        initComponents();
        
        getFrame().addWindowListener(this);
        mInitCloseButton.setVisible(false);
        
        initBlueRose();
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        initDevices();
    }

    private void initBlueRose()
    {
        TcpCompatibleDevice device = new TcpCompatibleDevice();

        try {
            Log.log("Conectando a BlueRose");
            org.ugr.bluerose.Initializer.initialize(new File("config.xml"));
            org.ugr.bluerose.Initializer.initializeClient(device);
            DeviceListServant sv = new DeviceListServant();
            org.ugr.bluerose.Initializer.initializeServant(sv, device);
            Log.log("Conectado. Servicio iniciado.");

            ClientChangeEventListener ccl = new ClientChangeEventListener();
            ClientQueryEventListener cql = new ClientQueryEventListener();

            org.ugr.bluerose.events.EventHandler.addEventListener(ccl);
            org.ugr.bluerose.events.EventHandler.addEventListener(cql);
            Log.log("Conectado. Escuchadores iniciados.");
        } catch (Exception ex) {
            Log.log(ex.getMessage());
        }
    }
    
    private void initDevices()
    {
        Log.log("Iniciando gestor de dispositivos.");
        mDeviceManager = DeviceManager.instance();
        try {
            KNXDevice.init("169.254.111.10", "169.254.111.254", "3671");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        Value s1min = new Value(),
              s1max = new Value();
        s1min.setBoolean(false);
        s1max.setBoolean(true);
        
        Device d = null;
        try {
            d = new KNXBooleanDevice(
                    "bombilla1",
                    "Luz 1",
                    "simpleLight",
                    Device.ACCESS_READ_WRITE,
                    "0/0/1");
            mDeviceManager.put(d);
            Log.log("Bombilla 1 añadida -> "+d.getValue().toString());
        } catch (Exception e) {
            Log.log("Fallo añadiendo dispositivos");
        }
        
        try {
            d = new KNXBooleanDevice(
                    "bombilla2",
                    "Luz 2",
                    "simpleLight",
                    Device.ACCESS_READ_WRITE,
                    "0/0/2");
            mDeviceManager.put(d);
            Log.log("Bombilla 2 añadida -> "+d.getValue().toString());
        } catch (Exception e) {
            Log.log("Fallo añadiendo dispositivos");
        }
        
        try {
            d = new KNXScalingDevice(
                    "bombilla3",
                    "Luz 3",
                    "simpleLight",
                    Device.ACCESS_READ_WRITE,
                    "0/0/4");
            mDeviceManager.put(d);
            Log.log("Bombilla 3 añadida -> "+d.getValue().toString());
        } catch (Exception e) {
            Log.log("Fallo añadiendo dispositivos");
        }
    }

    private void closeConnection()
    {
        KNXDevice.close();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        mLight1Button = new javax.swing.JButton();
        mLight2Button = new javax.swing.JButton();
        mLight3Slider = new javax.swing.JSlider();
        mLight3Label = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        mInitCloseButton = new javax.swing.JButton();
        mCleanLogButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(koraserver2.KoraServer2App.class).getContext().getResourceMap(KoraServer2View.class);
        mLight1Button.setText(resourceMap.getString("mLight1Button.text")); // NOI18N
        mLight1Button.setName("mLight1Button"); // NOI18N
        mLight1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mLight1ButtonActionPerformed(evt);
            }
        });

        mLight2Button.setText(resourceMap.getString("mLight2Button.text")); // NOI18N
        mLight2Button.setName("mLight2Button"); // NOI18N
        mLight2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mLight2ButtonActionPerformed(evt);
            }
        });
        
        mLight3Slider.setName("mLight3Button"); // NOI18N
        mLight3Slider.setMinimum(0);
        mLight3Slider.setMaximum(100);
        mLight3Slider.setValue(0);
        mLight3Slider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e)
            {
                
                mLight3SliderValueChanged(e);
            }});

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        mInitCloseButton.setText(resourceMap.getString("mInitCloseButton.text")); // NOI18N
        mInitCloseButton.setName("mInitCloseButton"); // NOI18N
        mInitCloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mInitCloseButtonActionPerformed(evt);
            }
        });

        mCleanLogButton.setText(resourceMap.getString("mCleanLogButton.text")); // NOI18N
        mCleanLogButton.setName("mCleanLogButton"); // NOI18N
        mCleanLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mCleanLogButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(mLight1Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mLight2Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mLight3Slider))
                    .addComponent(mInitCloseButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mCleanLogButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mLight1Button)
                    .addComponent(mLight2Button)
                    .addComponent(mLight3Slider))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(mCleanLogButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mInitCloseButton)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void mInitCloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mInitCloseButtonActionPerformed
        if(!mConnected){
            initBlueRose();
            mInitCloseButton.setText("Cerrar servicio");
        } else {
            closeConnection();
            mInitCloseButton.setText("Iniciar servicio");
        }

        mConnected = !mConnected;
    }//GEN-LAST:event_mInitCloseButtonActionPerformed


    private void mLight1ButtonActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_mLight1ButtonActionPerformed
        Device dev = mDeviceManager.getDevice("bombilla1");
        boolean b = dev.getValue().getBoolean();
        Value newValue = new Value();
        newValue.setBoolean(!b);
        try {
            dev.setValue(newValue);
            Event e = new Event(DeviceChangeEvent.TOPIC);

            Value deviceName = new Value(),
                  deviceValue = new Value();

            deviceName.setString("bombilla1");
            
            deviceValue.setBoolean(!b);

            e.setMember("name", deviceName);
            e.setMember("value", deviceValue);
            try{
                org.ugr.bluerose.events.EventHandler.publish(e, false);
            } catch(Throwable t){
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_mLight1ButtonActionPerformed
    
    private void mLight2ButtonActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_mLight2ButtonActionPerformed
        Device dev = mDeviceManager.getDevice("bombilla2");
        boolean b = dev.getValue().getBoolean();
        Value newValue = new Value();
        newValue.setBoolean(!b);
        try {
            dev.setValue(newValue);
            Event e = new Event(DeviceChangeEvent.TOPIC);

            Value deviceName = new Value(),
                  deviceValue = new Value();

            deviceName.setString("bombilla2");
            
            deviceValue.setBoolean(!b);

            e.setMember("name", deviceName);
            e.setMember("value", deviceValue);
            try{
                org.ugr.bluerose.events.EventHandler.publish(e, false);
            } catch(Throwable t){
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_mLight2ButtonActionPerformed

    private void mLight3SliderValueChanged(ChangeEvent evt)
    {//GEN-FIRST:event_mLight3ButtonActionPerformed
        JSlider source = (JSlider)evt.getSource();  // get the slider
        if (!source.getValueIsAdjusting()) {
            Device dev = mDeviceManager.getDevice("bombilla3");
            int v = source.getValue();
            Value val = new Value();
            val.setInteger(v);
            try {
                dev.setValue(val);
                Event e = new Event(DeviceChangeEvent.TOPIC);

                Value deviceName = new Value();

                deviceName.setString("bombilla2");

                e.setMember("name", deviceName);
                e.setMember("value", val);
                org.ugr.bluerose.events.EventHandler.publish(e, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }//GEN-LAST:event_mLight3ButtonActionPerformed

    private void mCleanLogButtonActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_mCleanLogButtonActionPerformed
        
    }//GEN-LAST:event_mCleanLogButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTextArea jTextArea1;
    protected javax.swing.JButton mCleanLogButton;
    protected javax.swing.JButton mInitCloseButton;
    protected javax.swing.JButton mLight1Button;
    protected javax.swing.JButton mLight2Button;
    protected JSlider mLight3Slider;
    protected JLabel mLight3Label;
    protected javax.swing.JPanel mainPanel;
    protected javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        try{
            KNXDevice.close();
        } catch(Exception ex){
            
        }
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
        // TODO Auto-generated method stub
        
    }

}
