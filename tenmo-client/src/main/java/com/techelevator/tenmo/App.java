package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    public static final int EXIT_APP = 0;
    public static final int VIEW_CURRENT_BALANCE = 1;
    public static final int VIEW_PAST_TRANSFERS = 2;
    public static final int VIEW_PENDING_REQUESTS = 3;
    public static final int SEND_TE_BUCKS = 4;
    public static final int REQUEST_TE_BUCKS = 5;
    public static final int REGISTER_USER = 1;
    public static final int LOGIN_USER = 2;


    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private TransferService transferService;

    private AuthenticatedUser currentUser;


    private AccountService accountService;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            // TODO: Instantiate services that require the current user to exist here
            this.accountService = new AccountService();
            this.transferService = new TransferService();
            accountService.setAuthToken(currentUser.getToken());
            transferService.setAuthToken(currentUser.getToken());
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != EXIT_APP && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == REGISTER_USER) {
                handleRegister();
            } else if (menuSelection == LOGIN_USER) {
                handleLogin();
            } else if (menuSelection != EXIT_APP) {
                consoleService.printMessage("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        consoleService.printMessage("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            consoleService.printMessage("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != EXIT_APP) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == VIEW_CURRENT_BALANCE) {
                viewCurrentBalance();
            } else if (menuSelection == VIEW_PAST_TRANSFERS) {
                viewTransferHistory();
            } else if (menuSelection == VIEW_PENDING_REQUESTS) {
                viewPendingRequests();
            } else if (menuSelection == SEND_TE_BUCKS) {
                sendBucks();
            } else if (menuSelection == REQUEST_TE_BUCKS) {
                requestBucks();
            } else if (menuSelection == EXIT_APP) {
                continue;
            } else {
                consoleService.printMessage("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		consoleService.printMessage("$" + accountService.getUserBalance().toString());
	}

	private void viewTransferHistory() {
        int choice = 0;
        while (choice != 1 && choice != 2) {
            choice = consoleService.promptForInt("Enter 1) to view all transfers, and 2) to view a specific transfer.");
        }

        if (choice == 1) {
            for (Transfer transfer : transferService.getUserTransfers()) {
                String type = "";
                String status = "";
                String id = String.valueOf(transfer.getTransferId());
                String fromUser = String.valueOf(transfer.getFromUserId());
                String toUser = String.valueOf(transfer.getToUserId());
                String amount = String.valueOf(transfer.getTransferAmount());

                int statusInt = transfer.getTransferStatus();
                int typeInt = transfer.getTransferType();
                if (statusInt == 1) {
                    status = "Pending";
                } else if (statusInt == 2) {
                    status = "Approved";
                } else if (statusInt == 3) {
                    status = "Rejected";
                }

                if (typeInt == 2) {
                    type = "Send TE Bucks";
                } else {
                    type = "Request TE Bucks";
                }

                consoleService.printTransfer(id, type, fromUser, toUser, amount, status);
            }
        } else {
            int id = consoleService.promptForInt("Enter a transfer ID: ");
            Transfer requestedTransfer = transferService.getTransferById(id);
            consoleService.printTransfer(
                    String.valueOf(requestedTransfer.getTransferId()),
                    String.valueOf(requestedTransfer.getTransferType()) ,
                    String.valueOf(requestedTransfer.getFromUserId()) ,
                    String.valueOf(requestedTransfer.getToUserId()) ,
                    String.valueOf(requestedTransfer.getTransferAmount()),
                    String.valueOf(requestedTransfer.getTransferStatus()));
        }
    }

        private void viewPendingRequests() {
            // TODO Auto-generated method stub

        }

        private void sendBucks() {
            Transfer transfer = new Transfer();
            transfer.setFromUserId(currentUser.getUser().getId());
            consoleService.printUserNameAndIdHeader();
            for(User user : transferService.getUserNameList()){
                consoleService.printUserNamesAndIds(user.getUsername(), String.valueOf(user.getId()));
            }
            consoleService.printMessage("\n");
            int idEntry = consoleService.promptForInt("Enter the ID of the user you'd like to send money to (0 to cancel): ");
            if (idEntry == 0){
                return;
            } else {
                transfer.setToUserId(idEntry);

                BigDecimal transferAmount = (consoleService.promptForBigDecimal("How much money would you like to send? "));
                while (transferAmount.compareTo(BigDecimal.ZERO) <= 0
                        || transferAmount.compareTo(accountService.getUserBalance()) == 1){
                    consoleService.printMessage("Enter a number greater than zero, and not greater than your total account balance");
                    transferAmount = consoleService.promptForBigDecimal("How much money would you like to send? ");
                }
                transfer.setTransferAmount(transferAmount);
                transfer.setTransferType(2);
                transferService.sendMoney(transfer);
            }
            consoleService.printMessage("You sent user " + transfer.getToUserId() + " $" + transfer.getTransferAmount() + ".");
            consoleService.printMessage("Your current balance is: $" + accountService.getUserBalance());
        }




	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
