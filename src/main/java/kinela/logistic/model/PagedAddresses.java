package kinela.logistic.model;

import java.util.ArrayList;
import java.util.List;

public class PagedAddresses {
	
	private List<Address> addressList = new ArrayList<Address>();
	private long size = 0;
	
	public PagedAddresses(List<Address> addresses, long size) {
		this.addressList = addresses;
		this.size = size;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
