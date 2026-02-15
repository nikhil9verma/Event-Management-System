import { useEffect, useState } from "react";
import { api } from "../../../lib/api";

interface RoleRequest {
  id: number;
  name: string;
  email: string;
  requestedAt: string;
}

const RoleRequests = () => {
  const [requests, setRequests] = useState<RoleRequest[]>([]);
  const [loading, setLoading] = useState(true);

  const fetchRequests = async () => {
    try {
      const res = await api.get("/role-requests/pending");
      setRequests(res.data);
    } catch (err) {
      console.error("Failed to load requests");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRequests();
  }, []);

  const handleAction = async (id: number, action: "approve" | "reject") => {
    try {
      await api.put(`/role-requests/${id}/${action}`);
      setRequests((prev) => prev.filter((r) => r.id !== id));
    } catch (err) {
      console.error("Action failed");
    }
  };

  if (loading) return <div className="text-white p-6">Loading...</div>;

  return (
    <div className="p-6 text-white">
      <h1 className="text-2xl font-semibold mb-6">
        Pending Host Requests
      </h1>

      {requests.length === 0 ? (
        <div>No pending requests.</div>
      ) : (
        <div className="space-y-4">
          {requests.map((req) => (
            <div
              key={req.id}
              className="bg-slate-800 p-4 rounded flex justify-between items-center"
            >
              <div>
                <div className="font-semibold">{req.name}</div>
                <div className="text-sm text-slate-400">
                  {req.email}
                </div>
              </div>

              <div className="flex gap-3">
                <button
                  onClick={() => handleAction(req.id, "approve")}
                  className="bg-green-600 px-3 py-1 rounded"
                >
                  Approve
                </button>

                <button
                  onClick={() => handleAction(req.id, "reject")}
                  className="bg-red-600 px-3 py-1 rounded"
                >
                  Reject
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default RoleRequests;
