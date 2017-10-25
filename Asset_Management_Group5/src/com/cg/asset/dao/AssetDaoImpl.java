package com.cg.asset.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.asset.dbutil.DbUtil;
import com.cg.asset.dto.Asset;
import com.cg.asset.exception.AssetException;



public  class AssetDaoImpl implements IAssetDao {
	Connection conn = null;
	
	Logger logger = Logger.getRootLogger();
	public AssetDaoImpl()
	{
		PropertyConfigurator.configure("log4j.properties");
	}
	
	
	public String loginDetails(Asset b) throws AssetException
	{
		
		String message=null;
		int c=0;
		
		try {
			conn=DbUtil.getConnections();
			String sql="Select userName,UserPassword from User_Master";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				
				String name=rs.getString(1);
				String pass=rs.getString(2);
				
				
				if((name.equals(b.getUserName())&& (pass.equals(b.getPassword()))))
						{
					c=1;
						}
				
				
			}
			if(c==1)
			{
				logger.info("Login Successfully");
				message="Login Successfull!!!";
				
			}
			else
			{
				logger.error("Exception Occured");
				message="Invalid UserName or Password";
				
			}
			
			
			
		} catch (IOException e) {
			logger.error("IO Exception Occured");
			throw new AssetException("INVALID INPUT");
			
		} catch (SQLException e) {
			logger.error("SQL Exception Occured");
			throw new AssetException("Could not retrieve login details");
		}
		
		
		return message;
		
	}

	public int getUserType(String string,Asset b) throws AssetException {
		 int a=0;
		try {
			conn=DbUtil.getConnections();
			String sql="select * from User_Master";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				String usertype=rs.getString(4);
				String name=rs.getString(2);
				String pass=rs.getString(3);
				
				if(usertype.equalsIgnoreCase(string) && name.equals(b.getUserName())&& pass.equals(b.getPassword()))
				{
					a=1;
					logger.info("Valid user Found");
				}
				
			}
		} catch (IOException e) {
			logger.error("IO Exception Occured");
			throw new AssetException("INVALID INPUT");
		
		} catch (SQLException e) {
			logger.error("SQL Exception Occured");
			throw new AssetException("Could not retrieve user type");
		}
		
		
		return a;
	}



	public int addDetails(Asset b) throws AssetException {
		// TODO Auto-generated method stub
		int n=0;
		try {
			int allocQuantity=0;
			conn=DbUtil.getConnections();
			String sql="insert into Asset values(seq_asset_id.nextval,?,?,?,?)";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			
			ps.setString(1, b.getAssetName());
			ps.setString(2,b.getAssetDes() );
			ps.setInt(3, b.getQuantity());
			ps.setInt(4,allocQuantity);
			
			 n=ps.executeUpdate();
			
			
		} catch (IOException e) {
			throw new AssetException("INVALID INPUT");
			
		} catch (SQLException e) {
			throw new AssetException("Could not add asset");
		}
		
		return n;
	}
	@Override
	public int validate(String assetName1) throws AssetException {
		// TODO Auto-generated method stub
		ArrayList<String> list=new ArrayList<String>();
		int c=0;
		try {
			conn=DbUtil.getConnections();
			String sql1="select assetName from asset";
			PreparedStatement ps1=conn.prepareStatement(sql1);
			ResultSet rs=ps1.executeQuery();
			while(rs.next())
			{
				list.add(rs.getString(1));
			}
			if(list.contains(assetName1))
				c=1;
			else
				c=0;
		
	}
		catch (IOException e) {
			throw new AssetException("INVALID INPUT");
			
		} catch (SQLException e) {
			throw new AssetException("Could not modify");
		}
		
		return c;
	}
	
	@Override
	public int modifyDesc(String assetDesc1,String assetName1) throws AssetException {
		// TODO Auto-generated method stub
		int n=0;
		try {
			conn=DbUtil.getConnections();
			String sql="update asset set assetDes=? where assetName=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, assetDesc1);
			ps.setString(2, assetName1);
			 n=ps.executeUpdate();
		} catch (IOException e) {
			
			throw new AssetException("INVALID INPUT");
		} catch (SQLException e) {
			
			throw new AssetException("Could not modify");
		}
		return n;
	}

	
	public int modifyAsset(String assetName1,int quantity1,int flag) throws AssetException {
		// TODO Auto-generated method stub
		
			int n=0;
				if(flag==1)
				{
				
				try {
					conn=DbUtil.getConnections();
					String sql="update Asset set Quantity=Quantity+? where AssetName=?";
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setInt(1, quantity1);
					ps.setString(2, assetName1);
					 n=ps.executeUpdate();
				} catch (IOException e) {
					throw new AssetException("INVALID INPUT");
					
				} catch (SQLException e) {
					throw new AssetException("Could not modify");
				}
				
				
			}
				
				if(flag==2)
				{
					try {
						conn=DbUtil.getConnections();
						String sql="update Asset set Quantity=Quantity-? where AssetName=?";
						PreparedStatement ps=conn.prepareStatement(sql);
						ps.setInt(1, quantity1);
						ps.setString(2, assetName1);
						 n=ps.executeUpdate();
					} catch (IOException e) {
						throw new AssetException("INVALID INPUT");
						
					} catch (SQLException e) {
						throw new AssetException("Could not modify");
					}
				}
				return n;
	
	}
	
	public ArrayList<Asset> viewRequestsByManager(int mgrId) throws AssetException
	{

		ArrayList<Asset> list= new ArrayList<Asset>();
		
		try{
			conn=DbUtil.getConnections();
		String sql = "SELECT * FROM Asset s JOIN Asset_Allocation l ON s.Assetid = l.Assetid where mgrId=?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, mgrId);
		
      
		ResultSet rs = pst.executeQuery();
		
		
	
		
		while(rs.next())
		{
			
			
			int assetId = rs.getInt(1);
			String assetName = rs.getString(2);
			String assetDes = rs.getString(3);
			int quantity = rs.getInt(4);
			int quantityDemanded=rs.getInt(5);
			int allocationId= rs.getInt(6);
			int empNo  = rs.getInt(8);
		
			String status=rs.getString(11);
			int managerId=rs.getInt(12);
			
			
		
			 
			list.add(new Asset(allocationId,assetId,empNo,assetName,assetDes,quantity,quantityDemanded,status,managerId));
			logger.info("Request Viewed Successfully");
			
		}
		}catch( SQLException | IOException e)
		{
			logger.error("Exception Occured");
			throw new AssetException("Could not retrieve");
			
		}
		
		
		
		return list ;
	}
	
	
	
	
	public Asset raiseRequest(Asset bean) throws AssetException
	{

	
		try {
			
			conn=DbUtil.getConnections();
			
	
			
		String query="SELECT empNo FROM Employee where Ename=?";
		PreparedStatement empStr=conn.prepareStatement(query);
		empStr.setString(1, bean.getEmployeeName());
		ResultSet empRs=empStr.executeQuery();
		empRs.next();
		bean.setEmpNo(empRs.getInt(1));
		
		
	
	
		String sql="Select assetId FROM Asset WHERE AssetName = ? ";
		PreparedStatement str=conn.prepareStatement(sql);
		str.setString(1,bean.getAssetName());
        
		ResultSet rs1=str.executeQuery();
		while(rs1.next())
		{
			
			bean.setAssetId(rs1.getInt(1));
			
		}	
			
		
		logger.info("Request Raised Successfully");
		
		}catch(IOException | SQLException e)
		{
			logger.error("Exception Occured");
			throw new AssetException("Could not retrieve");
			
		}
		return bean;
	}
	public int insertRequests(Asset bean) throws AssetException
	{
	
       int row=0;
       
       int allocationId=-1;
		try {
	
			conn=DbUtil.getConnections();
			
			
			
			String q="SELECT mgr FROM EMPLOYEE where empNo=?";
			PreparedStatement empStr1=conn.prepareStatement(q);		
			empStr1.setInt(1,bean.getEmpNo());
			ResultSet rsc=empStr1.executeQuery();
			rsc.next();
			int mgr=rsc.getInt(1);
			
			String q1="SELECT empNo FROM USER_MASTER WHERE UserName=?";
			PreparedStatement empStr2=conn.prepareStatement(q1);		
			empStr2.setString(1,bean.getUserName());
			ResultSet rsi=empStr2.executeQuery();
			rsi.next();
			int mgrId=rsi.getInt(1);
		     String status="Pending";
			if(mgr==mgrId)
			{
			bean.setMgrId(mgr);
			String query="insert into asset_allocation values(seq_allocId.nextval,?,?,NULL,NULL,?,?)";
			
			PreparedStatement str=conn.prepareStatement(query);
			
			str.setInt(1,bean.getAssetId());
			
			str.setInt(2,bean.getEmpNo());
			str.setString(3,status);
			str.setInt(4,bean.getMgrId());
			
		
			
			row= str.executeUpdate();
	
			
			PreparedStatement pst=conn.prepareStatement("update asset set allocatedQuantity=? where assetId=?");
			pst.setInt(1, bean.getAllocatedQuantity());
			
			pst.setInt(2, bean.getAssetId());
			
			pst.executeUpdate();
			logger.info("Request Inserted Successfully");
			
		
			if(row==1)
			{
			String sql="select seq_allocId.currval from dual";
			Statement str1=conn.createStatement();	
			ResultSet rs=str1.executeQuery(sql);
			rs.next();
			
			allocationId=rs.getInt(1);
			}
			}
			else{
				return -1;
			}
		
		}catch(IOException | SQLException e)
		{
			logger.error("IO Exception Occured");
			throw new AssetException("Could not retrieve");
			
		}	
		
		return allocationId;
			
	}
	
	
	

		    public int checkAsset(int allocId) throws AssetException
		    {
		    int checkQuantity;
		    try{
			conn=DbUtil.getConnections();
			
			String q="select quantity-allocatedQuantity from asset where assetId=(SELECT assetId FROM asset_allocation where allocationId=?)";
			PreparedStatement psq=conn.prepareStatement(q);
			psq.setInt(1, allocId);
			ResultSet rs1=psq.executeQuery();
			rs1.next();
			checkQuantity=rs1.getInt(1);
			logger.info("Asset Checked Successfully");
			
		    }catch(IOException | SQLException e)
			{
		    	logger.error("Exception Occured");
				throw new AssetException("Could not update");
				
			}
		    return checkQuantity;
		    }
			
			public int approveRequest(int allocationId) throws AssetException
			{
				int row=0;
				int result = 0;
				try{
				conn=DbUtil.getConnections();
			
						
				String query="update asset_allocation set Allocation_date=sysdate ,release_date=sysdate+10 where allocationId=?";
				PreparedStatement ps=conn.prepareStatement(query);
				ps.setInt(1,allocationId);
				row=ps.executeUpdate();
				logger.info("Request Approved Successfully");
				if(row == 1)
				{
					
					String sql1 = "select allocatedQuantity from asset where assetId=(SELECT assetid from asset_allocation where allocationid = ?)"; 
					PreparedStatement pst1=conn.prepareStatement(sql1);
					pst1.setInt(1, allocationId);
					ResultSet rs= pst1.executeQuery();
					rs.next();
					int quantity=rs.getInt(1);
					
					
					String sql="update asset set allocatedQuantity=allocatedQuantity-? , quantity = quantity-?  where assetId=(SELECT assetid from asset_allocation where allocationid = ?)";
					PreparedStatement pst=conn.prepareStatement(sql);
					pst.setInt(1, quantity);
					pst.setInt(2, quantity);
					pst.setInt(3, allocationId);
					result =pst.executeUpdate();
					
				}
				
				
			}catch(IOException | SQLException e)
			{
				logger.error("IO Exception Occured");
				throw new AssetException("Could not update");
				
			}
				return result;
			}
			
			
			
			

		
		
		


			
	public void setStatus(int allocId,String status) throws AssetException
	{
		try {
			Connection conn = DbUtil.getConnections();
			String updateStatus="update asset_allocation set status=? where AllocationId=?";
			PreparedStatement ps=conn.prepareStatement(updateStatus);
			ps.setString(1, status);
			ps.setInt(2,allocId);
			ps.executeUpdate();
			logger.info("Status Set Successfully");
			
			
		}
		catch(IOException | SQLException e) {
			throw new AssetException("Could not retrieve");
			
		}
		
		
		
	}
		

	public ArrayList<Asset> retrieveDetails() throws AssetException {
			
			ArrayList<Asset> list = new ArrayList<Asset>();
			try {
				Connection conn = DbUtil.getConnections();
				
			
			String sql = "SELECT * FROM Asset s ,Asset_Allocation aa,Employee e,Department d where aa.assetId=s.assetId and e.dept_Id= d.dept_Id and aa.empno= e.empno";
			
			PreparedStatement pst = conn.prepareStatement(sql);
	     
	      
			ResultSet rs = pst.executeQuery();
			
			
			
			while(rs.next())
			{

				int assetId = rs.getInt(1);
				String assetName = rs.getString(2);
				String assetDes = rs.getString(3);
				int quantity = rs.getInt(4);
				int quantityDemanded=rs.getInt(5);
				int allocationId= rs.getInt(6);
				int empNo  = rs.getInt(8);
				String status=rs.getString(11);
				int mgrId=rs.getInt(12);
				String empName=rs.getString(14);
				String deptName=rs.getString(20);

				list.add(new Asset(allocationId,assetId,empNo,assetName,assetDes,quantity,quantityDemanded,status,mgrId,empName,deptName));
				logger.info("Details Retrieved Successfully");
			}
			
			}
			
			catch (IOException | SQLException  e) {
				throw new AssetException("Could not retrieve");
				
			}
			
			return list;
		}

	@Override
	public boolean doesIdExist(int retMgrId) throws AssetException {
		Connection conn;
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			conn = DbUtil.getConnections();
			
			String sql="Select MgrId from asset_allocation";
			Statement st=conn.createStatement();
			ResultSet rs= st.executeQuery(sql);
			while(rs.next())
			{
				list.add(rs.getInt(1));
				
				
				
			}
			if(list.contains(retMgrId))
			{
				logger.info("Manager Id exists ");
				return true;
			}
			
			
		} catch (IOException |SQLException e) {
			logger.error("IO Exception Occured");
			throw new AssetException("Could not retrieve");
		} 
		
		
		
		return false;
	}


	
}





