import { useEffect } from 'react';
import { useState } from 'react'
import { api } from '../../../lib/api'
import { useAuth } from '../../auth/AuthContext';


interface Event {
  id: number;
  title: string;
  description: string;
  eventDate: string;
  venue: string;
}



const Events = () => {
  const { role } = useAuth();

  const [applyLoading, setApplyLoading] = useState(false);
  const [applyMessage, setApplyMessage] = useState<string | null>(null);


  const [events, setevents] = useState<Event[]>([]);
  const [registeredIds, setregisteredIds] = useState<number[]>([]);
  const [loading, setloading] = useState(true);
  const [hasRequested, setHasRequested] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [eventsRes, regRes] = await Promise.all([
          api.get("/events"),
          api.get("/registrations/my")
        ]);

        setevents(eventsRes.data);

        const ids = regRes.data.map((r: any) => r.eventId);
        setregisteredIds(ids);

      } catch (error) {
        console.error("Error loading events", error);
      }
      finally {
        setloading(false);
      }
    };
    fetchData();
  }, [])
  const handleApplyForHost = async () => {
    try {
      setApplyLoading(true);
      setApplyMessage(null);

      await api.post("/role-requests/apply");

      setApplyMessage("Host request submitted successfully.");
      setHasRequested(true);
    } catch (error: any) {
      setApplyMessage(
        error.response?.data || "Failed to submit request."
      );
    } finally {
      setApplyLoading(false);
    }
  };
  const handleRegister = async (eventId: number) => {
    try {
      await api.post(`/registrations?eventId=${eventId}`);
    } catch (error) {
      console.error("Registrations failed", error);
    }
  };

  if (loading) return <div>Loading...</div>

  return (
    <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
      {events.map((event) => {
        const isRegistered = registeredIds.includes(event.id);

        return (
          <div
            key={event.id}
            className="bg-slate-900 border border-slate-800 rounded-2xl p-6 space-y-4"
          >
            <h3 className="text-lg font-semibold">
              {event.title}
            </h3>

            <p className="text-slate-400 text-sm">
              {event.description}
            </p>

            <button
              disabled={isRegistered}
              onClick={() => handleRegister(event.id)}
              className={`w-full py-2 rounded-lg font-medium transition ${isRegistered
                ? "bg-green-600 cursor-not-allowed"
                : "bg-indigo-600 hover:bg-indigo-500"
                }`}
            >
              {isRegistered ? "Registered" : "Register"}
            </button>
            {role === "STUDENT" && !hasRequested && (
              <div className="mb-6 flex justify-between items-center">
                <h2 className="text-2xl font-semibold text-white">
                  Available Events
                </h2>

                <button
                  onClick={handleApplyForHost}
                  disabled={applyLoading}
                  className="bg-indigo-600 hover:bg-indigo-700 px-4 py-2 rounded text-white transition disabled:opacity-50"
                >
                  {applyLoading ? "Submitting..." : "Apply for Host"}
                </button>

              </div>
            )}
            {applyMessage && (
              <div className="text-sm text-indigo-400 mt-2">
                {applyMessage}
              </div>
            )}

          </div>
        );
      })}
    </div>
  )
}

export default Events
