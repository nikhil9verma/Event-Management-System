import { useEffect, useState } from "react";
import { api } from "../../../lib/api";

interface Event {
  id: number;
  title: string;
  eventDate: string;
  venue: string;
}

interface Registration {
  id: number;
  status: string;
  registeredAt: string;
  event: Event;
}

const MyRegistrations = () => {
  const [registrations, setRegistrations] = useState<Registration[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRegistrations = async () => {
      try {
        const res = await api.get("/registrations/my");
        setRegistrations(res.data);
      } catch (error) {
        console.error("Failed to fetch registrations", error);
      } finally {
        setLoading(false);
      }
    };

    fetchRegistrations();
  }, []);

  if (loading) {
    return <div className="text-center mt-10">Loading...</div>;
  }

  if (registrations.length === 0) {
    return (
      <div className="text-center mt-10 text-slate-400">
        You have not registered for any events yet.
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto mt-10 space-y-6">
      <h2 className="text-3xl font-semibold mb-6">
        My Registrations
      </h2>

      {registrations.map((reg) => (
        <div
          key={reg.id}
          className="bg-slate-900 border border-slate-800 rounded-2xl p-6 flex justify-between items-center"
        >
          <div>
            <h3 className="text-lg font-semibold">
              {reg.event.title}
            </h3>

            <p className="text-sm text-slate-400">
              {new Date(reg.event.eventDate).toLocaleString()}
            </p>

            <p className="text-sm text-slate-400">
              Venue: {reg.event.venue}
            </p>
          </div>

          <div className="text-right">
            <span
              className={`px-4 py-1 rounded-full text-sm ${
                reg.status === "REGISTERED"
                  ? "bg-green-600"
                  : "bg-yellow-600"
              }`}
            >
              {reg.status}
            </span>

            <p className="text-xs text-slate-500 mt-2">
              Registered on{" "}
              {new Date(reg.registeredAt).toLocaleString()}
            </p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default MyRegistrations;
