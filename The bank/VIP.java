import java.time.LocalDate;
import java.time.Period;
import javax.swing.JOptionPane;

public class VIP extends Account {

	public VIP() {
		super("VIP",100000);
	}

	public void withDraw(Frame frame) { // it can be int
		long d1 = (long) creditCardDate.getYear() * 10000 + (long) creditCardDate.getMonthValue() * 100
				+ (long) creditCardDate.getDayOfMonth();
		long d2 = (long) LocalDate.now().getYear() * 10000 + (long) LocalDate.now().getMonthValue() * 100
				+ (long) LocalDate.now().getDayOfMonth();
		if (d2 - d1 != 0)
			withdrawLimit_perDay = 20000;

		while (true) {
			try {
				double money = Integer.parseInt(JOptionPane.showInputDialog(frame, "please enter amount of money"));
				if (money >= 1) {
					if (balance >= money) {

						if (withdrawLimit_perDay >= money) {
							balance -= money;
							withdrawLimit_perDay -= money;
							creditCardDate = LocalDate.now();
							JOptionPane.showMessageDialog(frame, "Successful operation");
							break;
						} else if (withdrawLimit_perDay < money && withdrawLimit_perDay != 0) {
							double aLimitForPrinting=0;
							balance -= withdrawLimit_perDay;
							aLimitForPrinting=withdrawLimit_perDay;
							withdrawLimit_perDay = 0;
							creditCardDate = LocalDate.now();
							JOptionPane.showMessageDialog(frame,
									"Successful operation but with just $"+aLimitForPrinting+" (this is the maximum allowable amount per day)");
							break;
						} else {
							JOptionPane.showMessageDialog(frame, "you reached the maximum allowable limit per day)");
							break;
						}
					} else if (balance < money && balance != 0) {
						if (balance >= withdrawLimit_perDay && withdrawLimit_perDay != 0) {
							JOptionPane.showMessageDialog(frame,
									"You can't take more than" + withdrawLimit_perDay + " today");
							money = withdrawLimit_perDay;
							balance -= withdrawLimit_perDay;
							withdrawLimit_perDay = 0;
							break;
						} else if (balance < withdrawLimit_perDay) {
							JOptionPane.showConfirmDialog(frame, "you can not take more than " + balance);
							money = balance;
							withdrawLimit_perDay -= balance;
							balance = 0;
							break;
						} else {
							JOptionPane.showConfirmDialog(frame, "you reached the maximum allowable limit per day)");
							break;
						}
					} else if (balance == 0) {

						if (indebtedness != 5000) {

							String[] options = { "yes", "no" };

							int i = JOptionPane.showOptionDialog(frame,
									"You haven't enough money , Do you accept to borrow with 10% Bank interest", "loan",
									JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
									options[0]);

							if (i == 0) {
								money = borrow(money);
								break;
							}
							break;
						} else {
							JOptionPane.showMessageDialog(frame, "you have reached the max loan");
							break;
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid amount,please enter at least 1$ : ");
				}
			} catch (Exception a) {
				JOptionPane.showMessageDialog(frame, "Error , Enter numbers");
			}
		}
	}

	public double borrow(double money) {

		if (money < (15000 - indebtedness)) {
			status = true;
			indebtedness += money;
			debitDate = LocalDate.now();
			return money;
		}

		else {
			debitDate = LocalDate.now();
			double restOfmoney = 15000 - indebtedness;
			indebtedness = 15000;
			status = true;
			return restOfmoney;
		}
	}

	public boolean isBlocked() { // it will be used to close accounts

		if (status) {
			Period period = Period.between(debitDate, LocalDate.now());
			int years = period.getYears();
			int months = period.getMonths();
			if (years >= 1 || months >= 4) {
				return true;
			}
		}
		return false;
	}

}