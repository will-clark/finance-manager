package org.willclark.finance.tags;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class MoneyTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private BigDecimal amount;
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
		
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
				
		try {
			out.print(Money.dollars(amount));
		}
		catch(IOException e) {
			// do nothing?
		}
		
        return SKIP_BODY;
    }

	public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
	
	public static class Money {

	    private static final Currency USD = Currency.getInstance("USD");
	    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

	    private BigDecimal amount;
	    private Currency currency;   

	    public static Money dollars(BigDecimal amount) {
	        return new Money(amount, USD);
	    }

	    private Money(BigDecimal amount, Currency currency) {
	        this(amount, currency, DEFAULT_ROUNDING);
	    }

	    private Money(BigDecimal amount, Currency currency, RoundingMode rounding) {
	        this.amount = amount;
	        this.currency = currency;

	        this.amount = amount.setScale(currency.getDefaultFractionDigits(), rounding);
	    }

	    public BigDecimal getAmount() {
	        return amount;
	    }

	    public Currency getCurrency() {
	        return currency;
	    }

	    @Override
	    public String toString() {
	        return getCurrency().getSymbol() + " " + getAmount();
	    }

	    public String toString(Locale locale) {
	        return getCurrency().getSymbol(locale) + " " + getAmount();
	    }   
	}
	
}