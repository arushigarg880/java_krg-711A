import java.util.*;
import java.time.LocalDate;

public class Main {

    static class Student {
        String uid;
        String name;
        int fineAmount;
        int currentBorrowCount;

        public Student(String uid, String name, int fineAmount, int currentBorrowCount) {
            this.uid = uid;
            this.name = name;
            this.fineAmount = fineAmount;
            this.currentBorrowCount = currentBorrowCount;
        }

        public void policyCheck() {
            if (fineAmount > 0) {
                throw new IllegalStateException("This student has pending fine");
            }
            if (currentBorrowCount >= 2) {
                throw new IllegalStateException("Borrow limit exceeded");
            }
        }
    }

    static class Asset {

        String assetId;
        String assetName;
        boolean available;
        int securityLevel;

        public Asset(String assetId, String assetName, boolean available, int securityLevel) {
            this.assetId = assetId;
            this.assetName = assetName;
            this.available = available;
            this.securityLevel = securityLevel;
        }

        public void policyCheck(String uid) {
            if (!available) {
                throw new IllegalStateException("Asset is not available");
            }

            if (securityLevel == 3 && !uid.startsWith("KRG")) {
                throw new SecurityException("Access denied due to high security asset");
            }
        }
    }

    static class CheckoutRequest {
        String uid;
        String assetId;
        int hoursRequested;

        public CheckoutRequest(String uid, String assetId, int hoursRequested) {
            this.uid = uid;
            this.assetId = assetId;
            this.hoursRequested = hoursRequested;
        }
    }

    static class ValidationUtil {

        public static void validateUid(String uid) {
            if (uid == null || uid.length() < 8 || uid.length() > 12 || uid.contains(" ")) {
                throw new IllegalArgumentException("Invalid UID format");
            }
        }

        public static void validateAssetId(String assetId) {
            if (assetId == null || !assetId.startsWith("LAB-")) {
                throw new IllegalArgumentException("Invalid AssetId format");
            }

            String remaining = assetId.substring(4);
            for (int i = 0; i < remaining.length(); i++) {
                if (!Character.isDigit(remaining.charAt(i))) {
                    throw new IllegalArgumentException("AssetId must end with digits");
                }
            }
        }

        public static void validateHours(int hrs) {
            if (hrs < 1 || hrs > 6) {
                throw new IllegalArgumentException("Hours must be between 1 and 6");
            }
        }
    }

    static class AssetStore {

        HashMap<String, Asset> assetMap = new HashMap<>();

        public void addAsset(Asset asset) {
            assetMap.put(asset.assetId, asset);
        }

        public Asset findAsset(String assetId) {
            Asset a = assetMap.get(assetId);

            if (a == null) {
                throw new NullPointerException("Asset not found: " + assetId);
            }

            return a;
        }

        public void markBorrowed(Asset a) {
            if (!a.available) {
                throw new IllegalStateException("Asset already borrowed");
            }
            a.available = false;
        }
    }

    static class CheckoutService {

        AssetStore store;
        HashMap<String, Student> studentMap;

        public CheckoutService(AssetStore store, HashMap<String, Student> studentMap) {
            this.store = store;
            this.studentMap = studentMap;
        }

        public String checkout(CheckoutRequest req)
                throws IllegalArgumentException, IllegalStateException,
                       SecurityException, NullPointerException {

            ValidationUtil.validateUid(req.uid);
            ValidationUtil.validateAssetId(req.assetId);
            ValidationUtil.validateHours(req.hoursRequested);

            Student student = studentMap.get(req.uid);
            if (student == null) {
                throw new NullPointerException("Student not found");
            }

            Asset asset = store.findAsset(req.assetId);

            student.policyCheck();
            asset.policyCheck(req.uid);

            if (req.hoursRequested == 6) {
                System.out.println("Note: Max duration selected.");
            }

            if (asset.assetName.contains("Cable") && req.hoursRequested > 3) {
                req.hoursRequested = 3;
                System.out.println("Policy applied: Cables max 3 hours.");
            }

            store.markBorrowed(asset);
            student.currentBorrowCount++;

            String date = LocalDate.now().toString().replace("-", "");
            return "TXN-" + date + "-" + req.assetId + "-" + req.uid;
        }
    }

    static class AuditLogger {

        public static void log(String msg) {
            System.out.println("Audit: " + msg);
        }

        public static void logError(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        Student s1 = new Student("KRG11771", "Arushi", 0, 0);
        Student s2 = new Student("ABC12345", "Riya", 100, 0);
        Student s3 = new Student("KRG99999", "Simran", 0, 2);

        HashMap<String, Student> studentMap = new HashMap<>();
        studentMap.put(s1.uid, s1);
        studentMap.put(s2.uid, s2);
        studentMap.put(s3.uid, s3);

        Asset a1 = new Asset("LAB-101", "HDMI Cable", true, 1);
        Asset a2 = new Asset("LAB-202", "Oscilloscope", true, 3);
        Asset a3 = new Asset("LAB-303", "Projector", false, 2);

        AssetStore store = new AssetStore();
        store.addAsset(a1);
        store.addAsset(a2);
        store.addAsset(a3);

        CheckoutService service = new CheckoutService(store, studentMap);

        CheckoutRequest r1 = new CheckoutRequest("KRG11771", "LAB-101", 5);
        CheckoutRequest r2 = new CheckoutRequest("KRG11771", "LAB-XYZ", 7);
        CheckoutRequest r3 = new CheckoutRequest("ABC12345", "LAB-202", 2);

        CheckoutRequest[] requests = { r1, r2, r3 };

        for (CheckoutRequest req : requests) {

            try {
                String receipt = service.checkout(req);
                System.out.println("SUCCESS: " + receipt);

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Input: " + e.getMessage());
                AuditLogger.logError(e);

            } catch (NullPointerException e) {
                System.out.println("Not Found: " + e.getMessage());
                AuditLogger.logError(e);

            } catch (SecurityException e) {
                System.out.println("Security Error: " + e.getMessage());
                AuditLogger.logError(e);

            } catch (IllegalStateException e) {
                System.out.println("Policy Error: " + e.getMessage());
                AuditLogger.logError(e);

            } finally {
                AuditLogger.log("Attempt finished for UID=" + req.uid +", asset=" + req.assetId);

            }
        }
    }

}
