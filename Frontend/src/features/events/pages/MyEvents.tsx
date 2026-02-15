import { useEffect, useState } from "react";
import { api } from "../../../lib/api";

interface Event {
  id: number;
  title: string;
  description: string;
  eventDate: string;
  venue: string;
  category: string;
  maxParticipants: number;
  registrationDeadline: string;
  posterUrl: string;
  hostName: string;
}

const MyEvents = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchMyEvents = async () => {
      try {
        const res = await api.get("/events/my");
        setEvents(res.data);
      } catch (error) {
        console.error("Failed to fetch my events", error);
      } finally {
        setLoading(false);
      }
    };

    fetchMyEvents();
  }, []);

  if (loading) {
    return <div className="text-center mt-12">Loading...</div>;
  }

  if (events.length === 0) {
    return (
      <div className="text-center mt-12 text-slate-400">
        You haven't created any events yet.
      </div>
    );
  }

  return (
    <div className="max-w-6xl mx-auto mt-10">
      <h2 className="text-3xl font-semibold mb-8">
        My Created Events
      </h2>

      <div className="grid md:grid-cols-2 gap-8">
        {events.map((event) => (
          <div
            key={event.id}
            className="bg-slate-900 border border-slate-800 rounded-3xl overflow-hidden shadow-lg"
          >
            {event.posterUrl && (
              <img
                src={event.posterUrl}
                alt={event.title}
                className="w-full h-48 object-cover"
              />
            )}

            <div className="p-6 space-y-3">
              <h3 className="text-xl font-semibold">
                {event.title}
              </h3>

              <p className="text-sm text-slate-400">
                {new Date(event.eventDate).toLocaleString()}
              </p>

              <p className="text-sm text-slate-400">
                Venue: {event.venue}
              </p>

              <p className="text-sm text-slate-400">
                Max Participants: {event.maxParticipants}
              </p>

              <p className="text-sm text-slate-400">
                Registration Deadline:{" "}
                {new Date(
                  event.registrationDeadline
                ).toLocaleString()}
              </p>

              <div className="flex gap-4 mt-4">
                <button className="flex-1 bg-indigo-600 hover:bg-indigo-500 py-2 rounded-xl transition">
                  Edit
                </button>

                <button className="flex-1 bg-red-600 hover:bg-red-500 py-2 rounded-xl transition">
                  Delete
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MyEvents;
