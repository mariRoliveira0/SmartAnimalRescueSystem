/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author marianarodriguesoliveira
 */
import client.ServiceDiscovery;

import generated.grpc.animalDetection.*;
import generated.grpc.rescueService.*;
import generated.grpc.vetservice.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.jmdns.ServiceInfo;
import javax.swing.*;
import java.awt.*;

public class MainGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Smart Animal Rescue System");
        frame.setSize(550, 650);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= ANIMAL =================
        JLabel animalTitle = new JLabel("Animal Detection");
        animalTitle.setBounds(20, 10, 200, 25);

        JTextField idField = new JTextField();
        idField.setBounds(20, 40, 150, 35);
        idField.setBorder(BorderFactory.createTitledBorder("Animal ID"));

        JTextField speciesField = new JTextField();
        speciesField.setBounds(180, 40, 150, 35);
        speciesField.setBorder(BorderFactory.createTitledBorder("Species"));

        JTextField locationField = new JTextField();
        locationField.setBounds(340, 40, 150, 35);
        locationField.setBorder(BorderFactory.createTitledBorder("Location"));

        JComboBox<String> conditionBox = new JComboBox<>(
                new String[]{"Healthy", "Mild", "Injured", "Critical"}
        );
        conditionBox.setBounds(20, 90, 200, 35);
        conditionBox.setBorder(BorderFactory.createTitledBorder("Condition"));

        JButton animalBtn = new JButton("Report Animal");
        animalBtn.setBounds(240, 95, 200, 30);

        // ================= RESCUE =================
        JLabel rescueTitle = new JLabel("Rescue Service");
        rescueTitle.setBounds(20, 140, 200, 25);

        JTextField rescueIdField = new JTextField();
        rescueIdField.setBounds(20, 170, 150, 35);
        rescueIdField.setBorder(BorderFactory.createTitledBorder("Animal ID"));

        JTextField rescueLocation = new JTextField();
        rescueLocation.setBounds(180, 170, 150, 35);
        rescueLocation.setBorder(BorderFactory.createTitledBorder("Location"));

        JComboBox<String> urgencyBox = new JComboBox<>(
                new String[]{"Low", "Medium", "High"}
        );
        urgencyBox.setBounds(340, 170, 150, 35);
        urgencyBox.setBorder(BorderFactory.createTitledBorder("Urgency"));

        JButton rescueBtn = new JButton("Request Rescue");
        rescueBtn.setBounds(180, 215, 200, 30);

        // ================= VET =================
        JLabel vetTitle = new JLabel("Veterinary Service");
        vetTitle.setBounds(20, 260, 200, 25);

        JTextField vetAnimalId = new JTextField();
        vetAnimalId.setBounds(20, 290, 150, 35);
        vetAnimalId.setBorder(BorderFactory.createTitledBorder("Animal ID"));

        JTextField treatmentField = new JTextField();
        treatmentField.setBounds(180, 290, 150, 35);
        treatmentField.setBorder(BorderFactory.createTitledBorder("Treatment"));

        JTextField vetNameField = new JTextField();
        vetNameField.setBounds(340, 290, 150, 35);
        vetNameField.setBorder(BorderFactory.createTitledBorder("Vet Name"));

        JButton vetBtn = new JButton("Add Treatment");
        vetBtn.setBounds(180, 335, 200, 30);

        // ================= OUTPUT =================
        JTextArea output = new JTextArea();
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setEditable(false);
        output.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(output);
        scroll.setBounds(30, 390, 470, 180);

        // ADD COMPONENTS
        frame.add(animalTitle);
        frame.add(idField);
        frame.add(speciesField);
        frame.add(locationField);
        frame.add(conditionBox);
        frame.add(animalBtn);

        frame.add(rescueTitle);
        frame.add(rescueIdField);
        frame.add(rescueLocation);
        frame.add(urgencyBox);
        frame.add(rescueBtn);

        frame.add(vetTitle);
        frame.add(vetAnimalId);
        frame.add(treatmentField);
        frame.add(vetNameField);
        frame.add(vetBtn);

        frame.add(scroll);

        frame.setVisible(true);

        // ================= ACTIONS =================
        // Animal
        animalBtn.addActionListener(e -> {
            try {
                if (idField.getText().isEmpty()) {
                    output.setText("Error: Animal ID cannot be empty");
                    return;
                }
                ServiceInfo si = ServiceDiscovery.discoverService("_animal._tcp.local.");

                ManagedChannel channel = ManagedChannelBuilder
                        .forAddress(si.getHostAddresses()[0], si.getPort())
                        .usePlaintext()
                        .build();

                AnimalDetectionServiceGrpc.AnimalDetectionServiceBlockingStub stub
                        = AnimalDetectionServiceGrpc.newBlockingStub(channel);

                AnimalReportResponse res = stub.reportAnimal(
                        AnimalReportRequest.newBuilder()
                                .setAnimalId(idField.getText())
                                .setSpecies(speciesField.getText())
                                .setLocation(locationField.getText())
                                .setCondition((String) conditionBox.getSelectedItem())
                                .build()
                );

                output.setText(
                        "REQUEST:\n"
                        + "animalId: \"" + idField.getText() + "\"\n"
                        + "species: \"" + speciesField.getText() + "\"\n"
                        + "location: \"" + locationField.getText() + "\"\n"
                        + "condition: \"" + conditionBox.getSelectedItem() + "\""
                        + "\n\nRESPONSE:\n"
                        + "message: \"" + res.getMessage() + "\"\n"
                        + "success: " + res.getSuccess()
                );

                channel.shutdown();

            } catch (Exception ex) {
                output.setText("Error: " + ex.getMessage());
            }
        });

        //  Rescue
        rescueBtn.addActionListener(e -> {
            try {
                ServiceInfo si = ServiceDiscovery.discoverService("_rescue._tcp.local.");

                ManagedChannel channel = ManagedChannelBuilder
                        .forAddress(si.getHostAddresses()[0], si.getPort())
                        .usePlaintext()
                        .build();

                RescueServiceGrpc.RescueServiceBlockingStub stub
                        = RescueServiceGrpc.newBlockingStub(channel);

                RescueResponse res = stub.requestRescue(
                        RescueRequest.newBuilder()
                                .setAnimalId(rescueIdField.getText())
                                .setLocation(rescueLocation.getText())
                                .setUrgency((String) urgencyBox.getSelectedItem())
                                .build()
                );

                output.setText(
                        "REQUEST:\n"
                        + "animalId: \"" + rescueIdField.getText() + "\"\n"
                        + "location: \"" + rescueLocation.getText() + "\"\n"
                        + "urgency: \"" + urgencyBox.getSelectedItem() + "\""
                        + "\n\nRESPONSE:\n"
                        + "rescueId: \"" + res.getRescueId() + "\"\n"
                        + "status: \"" + res.getStatus() + "\""
                );

                channel.shutdown();

            } catch (Exception ex) {
                output.setText("Error: " + ex.getMessage());
            }
        });

        // Vet
        vetBtn.addActionListener(e -> {
            try {
                ServiceInfo si = ServiceDiscovery.discoverService("_vet._tcp.local.");

                if (si == null) {
                    output.setText("Vet Service not found! Make sure server is running.");
                    return;
                }

                ManagedChannel channel = ManagedChannelBuilder
                        .forAddress(si.getHostAddresses()[0], si.getPort())
                        .usePlaintext()
                        .build();

                VeterinaryServiceGrpc.VeterinaryServiceBlockingStub stub
                        = VeterinaryServiceGrpc.newBlockingStub(channel);

                TreatmentResponse res = stub.addTreatment(
                        TreatmentRequest.newBuilder()
                                .setAnimalId(vetAnimalId.getText())
                                .setTreatment(treatmentField.getText())
                                .setVetName(vetNameField.getText())
                                .build()
                );

                output.setText(
                        "REQUEST:\n"
                        + "animalId: \"" + vetAnimalId.getText() + "\"\n"
                        + "treatment: \"" + treatmentField.getText() + "\"\n"
                        + "vetName: \"" + vetNameField.getText() + "\""
                        + "\n\nRESPONSE:\n"
                        + "message: \"" + res.getMessage() + "\"\n"
                        + "success: " + res.getSuccess()
                );

                channel.shutdown();

            } catch (Exception ex) {
                output.setText("Error: " + ex.getMessage());
            }
        });
    }
}
