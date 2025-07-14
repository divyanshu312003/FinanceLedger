import React, { useState } from 'react';

export default function App() {
  const [token, setToken] = useState('');
  const [regData, setRegData] = useState({ name: '', email: '', password: '', phoneNumber: '' });
  const [loginData, setLoginData] = useState({ email: '', password: '' });
  const [depositAmount, setDepositAmount] = useState('');
  const [accountInfo, setAccountInfo] = useState(null);
  const [downloadAccountNumber, setDownloadAccountNumber] = useState('');
  const [transferData, setTransferData] = useState({ to: '', amount: '', locationChange: false });
  const [otp, setOtp] = useState('');
  const [transactions, setTransactions] = useState([]);

  const API = 'http://localhost:8080';

  const handleRegister = async () => {
    const res = await fetch(`${API}/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(regData)
    });
    const msg = await res.text();
    alert(msg);
  };

  const handleLogin = async () => {
    const res = await fetch(`${API}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(loginData)
    });
    const data = await res.json();
    setToken(data.token);
    localStorage.setItem("token", data.token);
    alert('Logged in!');
  };

  const getAccount = async () => {
    const res = await fetch(`${API}/accounts/me`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    });
    const data = await res.json();
    setAccountInfo(data);
  };

  const deposit = async () => {
    const res = await fetch(`${API}/accounts/deposit?amount=${depositAmount}`, {
      method: 'POST',
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    });
    alert(await res.text());
  };

  const downloadCsv = async () => {
    const response = await fetch(`${API}/accounts/me/transactions/download`, {
      headers: { Authorization: `Bearer ${token}` }
    });

    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'transactions.csv';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
  };

  const requestOtp = async () => {
    const res = await fetch(`${API}/accounts/transfer/request-otp`, {
      method: 'POST',
      headers: { Authorization: `Bearer ${token}` }
    });
    alert(await res.text());
  };

  const transferAmount = async () => {
    const { to, amount, locationChange } = transferData;
    const res = await fetch(`${API}/accounts/transfer/confirm?to=${to}&amount=${amount}&locationChange=${locationChange}&otp=${otp}`, {
      method: 'POST',
      headers: { Authorization: `Bearer ${token}` }
    });
    alert(await res.text());
  };

  const fetchTransactions = async () => {
    const res = await fetch(`${API}/accounts/me/transactions`, {
      headers: { Authorization: `Bearer ${token}` }
    });
    const data = await res.json();
    setTransactions(data);
  };

  return (
    <div className="p-4 max-w-2xl mx-auto space-y-4">
      <h1 className="text-2xl font-bold">FinanceLedger API Tester</h1>

      <section>
        <h2 className="text-xl font-semibold">Register</h2>
        <input className="border m-1" placeholder="Name" onChange={e => setRegData({ ...regData, name: e.target.value })} />
        <input className="border m-1" placeholder="Email" onChange={e => setRegData({ ...regData, email: e.target.value })} />
        <input className="border m-1" placeholder="Password" type="password" onChange={e => setRegData({ ...regData, password: e.target.value })} />
        <input className="border m-1" placeholder="Phone Number" onChange={e => setRegData({ ...regData, phoneNumber: e.target.value })} />
        <button className="bg-blue-500 text-white p-1" onClick={handleRegister}>Register</button>
      </section>

      <section>
        <h2 className="text-xl font-semibold">Login</h2>
        <input className="border m-1" placeholder="Email" onChange={e => setLoginData({ ...loginData, email: e.target.value })} />
        <input className="border m-1" placeholder="Password" type="password" onChange={e => setLoginData({ ...loginData, password: e.target.value })} />
        <button className="bg-green-500 text-white p-1" onClick={handleLogin}>Login</button>
      </section>

      <section>
        <h2 className="text-xl font-semibold">Account Info</h2>
        <button className="bg-purple-500 text-white p-1" onClick={getAccount}>Fetch My Account</button>
        {accountInfo && <pre>{JSON.stringify(accountInfo, null, 2)}</pre>}
      </section>

      <section>
        <h2 className="text-xl font-semibold">Deposit</h2>
        <input className="border m-1" placeholder="Amount" onChange={e => setDepositAmount(e.target.value)} />
        <button className="bg-yellow-500 text-white p-1" onClick={deposit}>Deposit</button>
      </section>

      <section>
        <h2 className="text-xl font-semibold">Transfer</h2>
        <input className="border m-1" placeholder="To Account" onChange={e => setTransferData({ ...transferData, to: e.target.value })} />
        <input className="border m-1" placeholder="Amount" onChange={e => setTransferData({ ...transferData, amount: e.target.value })} />
        <label>
          <input type="checkbox" className="m-1" onChange={e => setTransferData({ ...transferData, locationChange: e.target.checked })} /> Location Change
        </label>
        <input className="border m-1" placeholder="Enter OTP" onChange={e => setOtp(e.target.value)} />
        <div className="flex space-x-2">
          <button className="bg-gray-600 text-white p-1" onClick={requestOtp}>Request OTP</button>
          <button className="bg-indigo-500 text-white p-1" onClick={transferAmount}>Confirm Transfer</button>
        </div>
      </section>

      <section>
        <h2 className="text-xl font-semibold">Download Transactions CSV</h2>
        <button className="bg-gray-800 text-white p-1" onClick={downloadCsv}>Download CSV</button>
      </section>

      <section>
        <h2 className="text-xl font-semibold">Fetch Transactions Log</h2>
        <button className="bg-pink-500 text-white p-1" onClick={fetchTransactions}>Fetch Transactions</button>
        {transactions.length > 0 && (
          <div className="mt-2 border p-2">
            <h3 className="font-bold mb-2">Transaction History</h3>
            <table className="w-full table-auto text-sm">
              <thead>
                <tr className="bg-gray-200">
                  <th className="px-2 py-1">ID</th>
                  <th className="px-2 py-1">From</th>
                  <th className="px-2 py-1">To</th>
                  <th className="px-2 py-1">Amount</th>
                  <th className="px-2 py-1">Type</th>
                  <th className="px-2 py-1">Date</th>
                </tr>
              </thead>
              <tbody>
                {transactions.map(txn => (
                  <tr key={txn.id} className="border-t">
                    <td className="px-2 py-1">{txn.id}</td>
                    <td className="px-2 py-1">{txn.fromAccount}</td>
                    <td className="px-2 py-1">{txn.toAccount}</td>
                    <td className="px-2 py-1">₹{txn.amount}</td>
                    <td className="px-2 py-1">{txn.type}</td>
                    <td className="px-2 py-1">{new Date(txn.timestamp).toLocaleString()}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>
    </div>
  );
}