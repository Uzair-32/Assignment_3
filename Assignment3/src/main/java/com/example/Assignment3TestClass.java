package com.example;

import com.example.Appointment_Scheduling.*;
import com.example.Health_Data_Handling.*;
import com.example.User_Management.*;
import com.example.ChatAndVideoConsultation.*;
import com.example.EmergencyAlertSystem.*;
import com.example.NotificationsAndReminders.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Assignment3TestClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AppointmentManager appointmentManager = new AppointmentManager();
        VitalsDatabase vitalsDatabase = new VitalsDatabase();
        NotificationService notificationService = new NotificationService();
        ReminderService reminderService = new ReminderService();
        Map<String, Patient> patientMap = new HashMap<>();
        Map<String, Doctor> doctorMap = new HashMap<>();
        Administrator admin = new Administrator();
        ChatServer chatServer = new ChatServer();
        EmailNotification emailReminder = new EmailNotification();
        SMSNotification smsReminder = new SMSNotification();

        boolean running = true;
        while (running) {
            System.out.println("\n========= ASSIGNMENT 3 - FULL SYSTEM MENU =========");
            System.out.println("1. Add Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. Assign Doctor to Patient");
            System.out.println("4. Input Patient Vitals (Check Emergency)");
            System.out.println("5. Trigger Panic Button");
            System.out.println("6. Schedule Appointment");
            System.out.println("7. Reschedule Appointment");
            System.out.println("8. Cancel Appointment");
            System.out.println("9. Approve Pending Appointment");
            System.out.println("10. Load Appointment Reminders");
            System.out.println("11. Send All Reminders");
            System.out.println("12. Start Chat");
            System.out.println("13. Export Chat");
            System.out.println("14. Import Chat");
            System.out.println("15. Search in Chat");
            System.out.println("16. Delete Chat");
            System.out.println("17. Start Video Call");
            System.out.println("18. Exit");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Patient ID (Pxxxxx): ");
                        String pid = scanner.nextLine();
                        System.out.print("Name: ");
                        String pname = scanner.nextLine();
                        System.out.print("Email: ");
                        String pemail = scanner.nextLine();
                        System.out.print("Phone: ");
                        String pphone = scanner.nextLine();
                        Patient patient = new Patient();
                        patient.setUserID(pid);
                        patient.setName(pname);
                        patient.setEmail(pemail);
                        patient.setPhoneNumber(pphone);
                        patient.setAccountStatus(true);
                        patientMap.put(pid, patient);
                        admin.addPatient(patient);
                        System.out.println("✅ Patient added.");
                        break;

                    case "2":
                        System.out.print("Doctor ID (Dxxxxx): ");
                        String did = scanner.nextLine();
                        System.out.print("Name: ");
                        String dname = scanner.nextLine();
                        System.out.print("Email: ");
                        String demail = scanner.nextLine();
                        System.out.print("Phone: ");
                        String dphone = scanner.nextLine();
                        System.out.print("Available Days (comma separated): ");
                        List<String> days = Arrays.asList(scanner.nextLine().split(","));
                        Doctor doctor = new Doctor();
                        doctor.setUserID(did);
                        doctor.setName(dname);
                        doctor.setEmail(demail);
                        doctor.setPhoneNumber(dphone);
                        doctor.setAvailableDays(days);
                        doctorMap.put(did, doctor);
                        admin.addDoctor(doctor);
                        System.out.println("✅ Doctor added.");
                        break;

                    case "3":
                        System.out.println("Available Patients: ");
                        for(Patient patient1 : admin.getManagedPatients()) {
                            System.out.println(patient1.getUserID());
                        }
                        System.out.println("Available Doctors: ");
                        for(Doctor doctor1 : admin.getManagedDoctors()) {
                            System.out.println(doctor1.getUserID());
                        }
                        System.out.print("Doctor ID: ");
                        String docID = scanner.nextLine();
                        System.out.print("Patient ID: ");
                        String patID = scanner.nextLine();
                        admin.assignDoctorToPatient(docID, patID);
                        System.out.println("✅ Assigned.");
                        break;

                    case "4":
                        System.out.println("Available Patients:");
                        for (Patient p : admin.getManagedPatients()) {
                            System.out.println("• " + p.getUserID() + " - " + p.getName());
                        }

                        System.out.print("Patient ID: ");
                        String vid = scanner.nextLine();
                        Patient vp = patientMap.get(vid);
                        if (vp == null) {
                            System.out.println("❌ Patient not found.");
                            break;
                        }

                        System.out.print("Heart Rate (Normal: 50 - 130 BPM): ");
                        int hr = Integer.parseInt(scanner.nextLine());

                        System.out.print("Oxygen Level (Normal: ≥ 90%): ");
                        int oxy = Integer.parseInt(scanner.nextLine());

                        System.out.print("Blood Pressure (Normal: 80 - 140 mmHg): ");
                        int bp = Integer.parseInt(scanner.nextLine());

                        System.out.print("Temperature (Normal: 35.0 - 39.0 °C): ");
                        double temp = Double.parseDouble(scanner.nextLine());

                        System.out.print("Respiratory Rate (Normal: 12 - 25 breaths/min): ");
                        int rr = Integer.parseInt(scanner.nextLine());

                        System.out.print("Glucose Level (Normal: 70 - 180 mg/dL): ");
                        int glucose = Integer.parseInt(scanner.nextLine());

                        System.out.print("Cholesterol Level (Normal: ≤ 240 mg/dL): ");
                        int cholesterol = Integer.parseInt(scanner.nextLine());

                        System.out.print("BMI (Normal: 18.5 - 30.0 kg/m²): ");
                        double bmi = Double.parseDouble(scanner.nextLine());

                        System.out.print("Hydration Level (Normal: 40.0% - 70.0%): ");
                        double hydration = Double.parseDouble(scanner.nextLine());

                        System.out.print("Stress Level (Normal: 0 - 8 /10): ");
                        int stress = Integer.parseInt(scanner.nextLine());

                        VitalSign v = new VitalSign(vid, hr, oxy, bp, temp, rr, glucose, cholesterol, bmi, hydration, stress, admin);
                        vitalsDatabase.addVitalSign(v);
                        EmergencyAlert ea = new EmergencyAlert(vp, v, notificationService);
                        ea.checkVitals();
                        break;


                    case "5":
                        System.out.println("Available Patients with vitals:");
                        for (String id : patientMap.keySet()) {
                            List<VitalSign> vlist = vitalsDatabase.findPatientViatls(id);
                            if (vlist != null && !vlist.isEmpty()) {
                                System.out.println("• " + id + " - " + patientMap.get(id).getName());
                            }
                        }
                        System.out.print("Patient ID for Panic: ");
                        String panID = scanner.nextLine();
                        Patient pp = patientMap.get(panID);
                        List<VitalSign> vs = vitalsDatabase.findPatientViatls(panID);
                        if (pp != null && !vs.isEmpty()) {
                            PanicButton panic = new PanicButton(new EmergencyAlert(pp, vs.get(vs.size() - 1), notificationService));
                            panic.pressButton();
                        } else {
                            System.out.println("❌ Data missing.");
                        }
                        break;

                    case "6":
                        System.out.println("Available Patients:");
                        for (Patient p : patientMap.values()) {
                            System.out.println("• " + p.getUserID() + " - " + p.getName());
                        }
                        System.out.println("Available Doctors:");
                        for (Doctor d : doctorMap.values()) {
                            System.out.println("• " + d.getUserID() + " - " + d.getName());
                        }
                        System.out.print("Appointment ID: ");
                        String aid = scanner.nextLine();
                        System.out.print("Patient ID: ");
                        String apid = scanner.nextLine();
                        System.out.print("Doctor ID: ");
                        String adid = scanner.nextLine();
                        System.out.print("Reason: ");
                        String reason = scanner.nextLine();
                        System.out.print("DateTime (yyyy-MM-dd HH:mm): ");
                        LocalDateTime dt = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        appointmentManager.scheduleAppointment(aid, apid, adid, admin, dt, reason);
                        if(dt.getHour() - LocalDateTime.now().getHour() < 24) {
                            // 🔔 Add reminders immediately
                            String message = "Reminder: You have an appointment with Dr. " + doctorMap.get(adid).getName() + " on " + dt;

                            // Create email & sms reminders
                            emailReminder = new EmailNotification(patientMap.get(apid).getEmail(), message, notificationService);
                            smsReminder = new SMSNotification(patientMap.get(apid).getPhoneNumber(), message, notificationService);

                            reminderService.addReminder(emailReminder);
                            reminderService.addReminder(smsReminder);

                            System.out.println("📬 Reminders added for this appointment.");
                        }
                        break;

                    case "7":
                        System.out.println("Available Patients:");
                        for (Patient p : patientMap.values()) {
                            System.out.println("• " + p.getUserID() + " - " + p.getName());
                        }
                        System.out.println("Enter the PatientID: ");
                        String patientID = scanner.nextLine();
                        System.out.println("Scheduled Appointments: ");
                        for (Appointment a : appointmentManager.getPatientUpcomingAppointments(patientID)) {
                            System.out.println("• " + a.getAppointmentID() + " - " + a.getAppointmentDateTime());
                        }
                        System.out.print("Appointment ID: ");
                        String rid = scanner.nextLine();
                        System.out.print("New DateTime: ");
                        LocalDateTime rdt = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        appointmentManager.reScheduleAppointment(rid, rdt);
                        break;

                    case "8":
                        System.out.println("Available Patients:");
                        for (Patient p : patientMap.values()) {
                            System.out.println("• " + p.getUserID() + " - " + p.getName());
                        }
                        System.out.println("Enter the PatientID: ");
                        String pID = scanner.nextLine();
                        System.out.println("All Appointments:");
                        for (Appointment a : appointmentManager.getPatientUpcomingAppointments(pID)) {
                            System.out.println("• " + a.getAppointmentID() + " - " + admin.findPatientbyID(pID).getName());
                        }

                        System.out.print("Appointment ID: ");
                        appointmentManager.cancelAppointment(scanner.nextLine());
                        break;

                    case "9":
                        System.out.println("Available Patients:");
                        for (Patient p : patientMap.values()) {
                            System.out.println("• " + p.getUserID() + " - " + p.getName());
                        }
                        System.out.println("Enter the PatientID: ");
                        String PID = scanner.nextLine();
                        System.out.println("Pending Appointments:");
                        for (Appointment a : appointmentManager.findSpecificStatusAppointments(Appointment.AppointmentStatus.PENDING)) {
                            System.out.println("• " + a.getAppointmentID() + " - " + admin.findPatientbyID(PID).getName());
                        }
                        System.out.print("Appointment ID: ");
                        appointmentManager.approveOrRejectAppointmentsByPatients(scanner.nextLine(), admin);
                        break;

                    case "10":
                        reminderService.loadUpcomingAppointmentReminders(appointmentManager, notificationService, admin);
                        System.out.println("✅ Loaded.");
                        break;

                    case "11":
                        reminderService.sendAllReminders();
                        break;

                    case "12":
                        startNewChat(scanner, chatServer);
                        break;

                    case "13":
                        System.out.print("Patient name: ");
                        String pn = scanner.nextLine();
                        System.out.print("Doctor name: ");
                        String dn = scanner.nextLine();
                        chatServer.exportChat(pn, dn);
                        break;

                    case "14":
                        File folder = new File("chats");
                        File[] files = folder.listFiles();
                        if (files == null || files.length == 0) {
                            System.out.println("No chat files found.");
                            break;
                        }
                        for (File f : files) System.out.println("• " + f.getName());
                        System.out.print("Enter filename to import: ");
                        chatServer.importChat(scanner.nextLine());
                        break;

                    case "15":
                        File[] allChats = new File("chats").listFiles();
                        System.out.println("Available Chat Files:");
                        if (allChats != null) {
                            for (File file : allChats) {
                                System.out.println("• " + file.getName());
                            }
                        }
                        System.out.print("Filename: ");
                        String f = scanner.nextLine();
                        System.out.print("Keyword: ");
                        System.out.println(chatServer.searchInChat(f, scanner.nextLine()));
                        break;

                    case "16":
                        File[] chatFiles = new File("chats").listFiles();
                        System.out.println("Available Chat Files:");
                        if (chatFiles != null) {
                            for (File file : chatFiles) {
                                System.out.println("• " + file.getName());
                            }
                        }
                        System.out.print("Filename: ");
                        chatServer.deleteChat(scanner.nextLine());
                        break;

                    case "17":
                        System.out.println("Available Doctors:");
                        for (Doctor d : doctorMap.values()) {
                            System.out.println("• " + d.getName());
                        }
                        System.out.println("Available Patients:");
                        for (Patient p : patientMap.values()) {
                            System.out.println("• " + p.getName());
                        }

                        System.out.print("Doctor name: ");
                        String doc = scanner.nextLine();
                        System.out.print("Patient name: ");
                        String pat = scanner.nextLine();
                        new VideoCall(doc, pat).startCall();
                        break;

                    case "18":
                        running = false;
                        System.out.println("👋 Exiting system. Goodbye!");
                        break;

                    default:
                        System.out.println("❌ Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void startNewChat(Scanner scanner, ChatServer server) {
        System.out.print("👨‍⚕️ Enter Doctor's name: ");
        String doctorName = scanner.nextLine();
        ChatClient doctor = new ChatClient(doctorName, server);

        System.out.print("🧑‍💼 Enter Patient's name: ");
        String patientName = scanner.nextLine();
        ChatClient patient = new ChatClient(patientName, server);

        System.out.println("\n💬 Start chatting! Type `exit` to end.\n");

        boolean chatting = true;
        boolean doctorTurn = false;

        while (chatting) {
            if (doctorTurn) {
                System.out.print("👨‍⚕️ " + doctorName + ": ");
                String msg = scanner.nextLine();
                if (msg.equalsIgnoreCase("exit")) break;
                doctor.sendMessage(msg);
                if (msg.equalsIgnoreCase("/video")) {
                    VideoCall vc = new VideoCall(doctorName, patientName);
                    vc.startCall();
                    continue;
                }
            } else {
                System.out.print("🧑‍💼 " + patientName + ": ");
                String msg = scanner.nextLine();
                if (msg.equalsIgnoreCase("exit")) break;
                patient.sendMessage(msg);
                if (msg.equalsIgnoreCase("/video")) {
                    VideoCall vc = new VideoCall(doctorName, patientName);
                    vc.startCall();
                    continue;
                }
            }
            doctorTurn = !doctorTurn;
        }

        System.out.println("\n📃 Final Chat Transcript:");
        doctor.viewAllMessages();

        server.exportChat(patientName, doctorName);
    }

    private static void showAvailableFiles() {
        File dir = new File("chats");
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".txt"));

        System.out.println("\n📂 Available Chat Files:");
        if (files != null && files.length > 0) {
            for (File file : files) {
                System.out.println("• " + file.getName());
            }
        } else {
            System.out.println("❌ No chat files found in /chats folder.");
        }
    }

}
