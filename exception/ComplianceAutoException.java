package com.cisco.ui.test.compliance.exception;

public class ComplianceAutoException extends Exception {
	private static final long serialVersionUID = 1L;

	public ComplianceAutoException() {
		super();
	}

	public ComplianceAutoException(final Throwable cause) {
		super(cause);
	}

	public ComplianceAutoException(final String messgae) {
		super(messgae);
	}

	public ComplianceAutoException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
