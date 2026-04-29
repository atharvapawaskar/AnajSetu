"use client";

import { useState, useEffect, useCallback } from "react";
import Link from "next/link";
import {
  Leaf, LogOut, Bell, Settings, Package, CheckCircle2,
  Recycle, FlaskConical, Clock, MapPin, ArrowRight,
  Loader2, X, TrendingUp, Flame
} from "lucide-react";

interface CompostItem {
  id: number;
  foodListing: {
    id: number;
    foodName: string;
    quantity: number;
    quantityUnit: string;
    pickupAddress: string;
    donorName?: string;
    createdAt?: string;
  };
  status: "REDIRECTED" | "COLLECTED" | "PROCESSED";
  redirectedAt?: string;
  collectedAt?: string;
  biogasOutputKg?: number;
}

const statusConfig = {
  REDIRECTED: {
    label: "Redirected",
    style: "bg-amber-100 text-amber-700 border border-amber-200",
    dot: "bg-amber-500 animate-pulse"
  },
  COLLECTED: {
    label: "Collected",
    style: "bg-blue-100 text-blue-700 border border-blue-200",
    dot: "bg-blue-500"
  },
  PROCESSED: {
    label: "Processed ♻️",
    style: "bg-green-100 text-green-700 border border-green-200",
    dot: "bg-green-500"
  },
};

export default function CompostDashboard() {
  const [items, setItems] = useState<CompostItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [centerName, setCenterName] = useState("Compost Center");
  const [centerId, setCenterId] = useState<number>(0);
  const [activeTab, setActiveTab] = useState<"PENDING" | "COLLECTED" | "PROCESSED">("PENDING");
  const [actionLoading, setActionLoading] = useState<number | null>(null);
  const [successMsg, setSuccessMsg] = useState("");

  // Load user from session
  useEffect(() => {
    const stored = sessionStorage.getItem("anajsetu_user");
    if (stored) {
      const user = JSON.parse(stored);
      setCenterName(user.orgName || user.name || "Compost Center");
      setCenterId(user.userId || user.id || 0);
    }
  }, []);

  // Fetch compost items
  const fetchItems = useCallback(async () => {
    if (!centerId) return;
    try {
      const res = await fetch(
        `http://localhost:8081/api/compost/center/${centerId}`
      );
      if (res.ok) setItems(await res.json());
    } catch {
      // silent
    } finally {
      setLoading(false);
    }
  }, [centerId]);

  useEffect(() => {
    if (centerId) {
      fetchItems();
      const interval = setInterval(fetchItems, 15000);
      return () => clearInterval(interval);
    }
  }, [centerId, fetchItems]);

  // Mark as Collected
  async function markCollected(id: number) {
    setActionLoading(id);
    try {
      const res = await fetch(
        `http://localhost:8081/api/compost/${id}/collected`,
        { method: "PUT" }
      );
      if (res.ok) {
        setSuccessMsg("Marked as collected ✅");
        fetchItems();
        setTimeout(() => setSuccessMsg(""), 3000);
      }
    } finally {
      setActionLoading(null);
    }
  }

  // Mark as Processed
 async function markProcessed(id: number, biogasKg: number) {
    setActionLoading(id);
    try {
      const res = await fetch(
        `http://localhost:8081/api/compost/${id}/processed/${biogasKg}`,
        { method: "PUT" }
      );
      if (res.ok) {
        setSuccessMsg(`Marked as processed ♻️ — ${biogasKg}kg biogas generated`);
        fetchItems();
        setTimeout(() => setSuccessMsg(""), 3000);
      }
    } finally {
      setActionLoading(null);
    }
  }

  // Filter by tab
  const pending   = items.filter((i) => i.status === "REDIRECTED");
  const collected = items.filter((i) => i.status === "COLLECTED");
  const processed = items.filter((i) => i.status === "PROCESSED");

  const tabItems =
    activeTab === "PENDING"   ? pending   :
    activeTab === "COLLECTED" ? collected : processed;

  const avatarLetter = centerName?.charAt(0)?.toUpperCase() || "C";

  const totalBiogas = items
    .filter(i => i.biogasOutputKg)
    .reduce((sum, i) => sum + (i.biogasOutputKg || 0), 0);

  return (
    <div className="min-h-screen bg-green-50/30">
      {/* Header */}
      <header className="sticky top-0 z-40 bg-white border-b border-gray-100 shadow-sm">
        <div className="max-w-6xl mx-auto px-4 sm:px-6 h-16 flex items-center justify-between">
          <Link href="/" className="flex items-center gap-2.5">
            <div className="bg-gradient-to-br from-green-600 to-green-700 rounded-xl p-2 shadow-md shadow-green-200">
              <Recycle className="text-white" size={18} />
            </div>
            <div className="leading-tight">
              <span className="font-extrabold text-gray-900 text-lg tracking-tight">Anaj</span>
              <span className="font-extrabold text-green-600 text-lg tracking-tight">Setu</span>
            </div>
          </Link>
          <div className="flex items-center gap-2">
            <button className="w-9 h-9 rounded-xl border border-gray-200 bg-white hover:bg-gray-50 flex items-center justify-center">
              <Bell size={16} className="text-gray-500" />
            </button>
            <button className="w-9 h-9 rounded-xl border border-gray-200 bg-white hover:bg-gray-50 flex items-center justify-center">
              <Settings size={16} className="text-gray-500" />
            </button>
            <div className="hidden sm:flex items-center gap-2 ml-1 pl-3 border-l border-gray-100">
              <div className="w-8 h-8 bg-green-600 rounded-xl flex items-center justify-center text-white text-xs font-bold">
                {avatarLetter}
              </div>
              <div className="leading-tight">
                <p className="text-sm font-bold text-gray-800">{centerName}</p>
                <p className="text-[11px] text-green-600 font-semibold">Compost Center</p>
              </div>
            </div>
            <Link href="/auth/login"
              className="w-9 h-9 rounded-xl border border-gray-200 bg-white hover:bg-red-50 flex items-center justify-center">
              <LogOut size={15} className="text-gray-400" />
            </Link>
          </div>
        </div>
      </header>

      <main className="max-w-6xl mx-auto px-4 sm:px-6 py-8 space-y-8">

        {/* Success Banner */}
        {successMsg && (
          <div className="flex items-center justify-between bg-green-50 border border-green-200 rounded-2xl px-6 py-4">
            <p className="text-sm font-bold text-green-800">{successMsg}</p>
            <button onClick={() => setSuccessMsg("")}><X size={16} className="text-green-400" /></button>
          </div>
        )}

        {/* Hero */}
        <div className="relative bg-gradient-to-br from-green-600 to-green-700 rounded-3xl px-8 py-7 overflow-hidden shadow-lg shadow-green-200">
          <div className="absolute top-0 right-0 w-64 h-64 bg-white/10 rounded-full -translate-y-1/2 translate-x-1/3 pointer-events-none" />
          <div className="relative flex items-center justify-between gap-6 flex-wrap">
            <div>
              <p className="text-green-200 text-xs font-bold uppercase tracking-widest mb-1">
                Compost Center Dashboard 🌿
              </p>
              <h1 className="text-white font-extrabold text-2xl sm:text-3xl tracking-tight mb-1">
                {centerName}
              </h1>
              <p className="text-green-100 text-sm max-w-sm">
                {pending.length} item{pending.length !== 1 ? "s" : ""} waiting to be collected
              </p>
            </div>
            
            <div className="flex gap-3">
              <div className="flex items-center gap-2 bg-white/15 border border-white/20 rounded-2xl px-5 py-3">
                <Recycle size={18} className="text-green-200" />
                <div>
                  <p className="text-white font-extrabold text-xl leading-tight">{processed.length}</p>
                  <p className="text-green-200 text-xs font-medium">batches processed</p>
                </div>
              </div>
              <div className="flex items-center gap-2 bg-white/15 border border-white/20 rounded-2xl px-5 py-3">
                <FlaskConical size={18} className="text-green-200" />
                <div>
                  <p className="text-white font-extrabold text-xl leading-tight">{totalBiogas} kg</p>
                  <p className="text-green-200 text-xs font-medium">biogas generated</p>
                </div>
              </div>
            </div>

          </div>
        </div>

        {/* Stats */}
        {/* --- MODIFIED GRID START --- */}
        <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
          {[
            { label: "Awaiting Collection", value: pending.length,   icon: Package,      bg: "bg-amber-100",   color: "text-amber-600"   },
            { label: "Collected",           value: collected.length, icon: TrendingUp,   bg: "bg-blue-100",    color: "text-blue-600"    },
            { label: "Processed",           value: processed.length, icon: FlaskConical, bg: "bg-green-100",   color: "text-green-600"   },
            { label: "Total Biogas (kg)",   value: totalBiogas,      icon: Leaf,         bg: "bg-emerald-100", color: "text-emerald-600" },
          ].map(({ label, value, icon: Icon, bg, color }) => (
            <div key={label} className="bg-white rounded-2xl border border-gray-100 shadow-sm px-5 py-5 flex items-center gap-4">
              <div className={`${bg} rounded-xl p-3 flex-shrink-0`}>
                <Icon size={20} className={color} />
              </div>
              <div>
                <p className="text-2xl font-extrabold text-gray-900 leading-tight">{value}</p>
                <p className="text-xs text-gray-500 font-medium mt-0.5">{label}</p>
              </div>
            </div>
          ))}
        </div>
        {/* --- MODIFIED GRID END --- */}

        {/* Main Card */}
        <div className="bg-white rounded-3xl border border-gray-100 shadow-sm overflow-hidden">
          {/* Tabs */}
          <div className="px-7 pt-6 pb-4 border-b border-gray-50 flex gap-2">
            {[
              { key: "PENDING",   label: "Awaiting Collection", count: pending.length   },
              { key: "COLLECTED", label: "Collected",           count: collected.length },
              { key: "PROCESSED", label: "Processed",           count: processed.length },
            ].map(({ key, label, count }) => (
              <button key={key}
                onClick={() => setActiveTab(key as "PENDING" | "COLLECTED" | "PROCESSED")}
                className={`flex items-center gap-1.5 text-sm font-bold px-4 py-2.5 rounded-xl transition-all ${
                  activeTab === key
                    ? "bg-green-600 text-white shadow-sm"
                    : "bg-gray-50 text-gray-500 hover:bg-gray-100 border border-gray-100"
                }`}>
                {label}
                <span className={`text-[10px] font-black px-1.5 py-0.5 rounded-full ${
                  activeTab === key ? "bg-white/25 text-white" : "bg-gray-200 text-gray-600"
                }`}>{count}</span>
              </button>
            ))}
          </div>

          {/* List */}
          <div className="px-7 py-5 space-y-3">
            {loading ? (
              <div className="flex flex-col items-center justify-center py-16">
                <Loader2 size={28} className="text-green-400 animate-spin mb-3" />
                <p className="text-sm text-gray-400">Loading compost items...</p>
              </div>
            ) : tabItems.length === 0 ? (
              <div className="flex flex-col items-center justify-center py-16 text-center">
                <div className="w-16 h-16 bg-green-50 rounded-2xl flex items-center justify-center mb-4">
                  <Recycle size={26} className="text-green-300" />
                </div>
                <h3 className="font-bold text-gray-700 mb-1">No items here</h3>
                <p className="text-sm text-gray-400">Nothing in this category yet.</p>
              </div>
            ) : (
              tabItems.map((item) => {
                const ds = statusConfig[item.status];
                return (
                  <div key={item.id}
                    className="flex items-start gap-4 bg-gray-50 hover:bg-green-50/60 border border-gray-100 hover:border-green-200 rounded-2xl px-5 py-5 transition-all">
                    <div className="w-10 h-10 bg-green-100 rounded-xl flex items-center justify-center flex-shrink-0 mt-0.5">
                      <Flame size={18} className="text-green-600" />
                    </div>
                    
                    <div className="flex-1 min-w-0">
                      <div className="flex items-start justify-between gap-3 flex-wrap">
                        <div>
                          <h3 className="font-bold text-gray-900 text-sm">
                            {item.foodListing.foodName || "Food Item"}
                          </h3>
                          <p className="text-xs text-gray-500 mt-0.5">
                            {item.foodListing.quantity} {item.foodListing.quantityUnit}
                          </p>
                        </div>
                        <span className={`flex items-center gap-1.5 text-[11px] font-bold px-2.5 py-1 rounded-full ${ds.style}`}>
                          <span className={`w-1.5 h-1.5 rounded-full ${ds.dot}`} />
                          {ds.label}
                        </span>
                      </div>

                      {/* ── Details row ── */}
                      <div className="mt-2.5 flex flex-wrap gap-x-4 gap-y-1">
                        {item.foodListing.donorName && (
                          <span className="flex items-center gap-1 text-xs text-gray-400">
                            <CheckCircle2 size={11} className="text-green-500" />
                            Donor: {item.foodListing.donorName}
                          </span>
                        )}
                        <span className="flex items-center gap-1 text-xs text-gray-400">
                          <MapPin size={11} />{item.foodListing.pickupAddress}
                        </span>
                        {item.redirectedAt && (
                          <span className="flex items-center gap-1 text-xs text-gray-400">
                            <Clock size={11} />Redirected: {new Date(item.redirectedAt).toLocaleString('en-IN')}
                          </span>
                        )}
                        {item.collectedAt && (
                          <span className="flex items-center gap-1 text-xs text-gray-400">
                            <Package size={11} />Collected: {new Date(item.collectedAt).toLocaleString('en-IN')}
                          </span>
                        )}
                        {item.biogasOutputKg && (
                          <span className="flex items-center gap-1 text-xs font-bold text-green-600 bg-green-50 border border-green-200 px-2 py-0.5 rounded-lg">
                            <FlaskConical size={11} />⚡ {item.biogasOutputKg} kg biogas generated
                          </span>
                        )}
                      </div>
                    </div>

                    {/* Action Buttons */}
                    <div className="flex flex-col gap-2 flex-shrink-0">
                      {item.status === "REDIRECTED" && (
                        <button onClick={() => markCollected(item.id)}
                          disabled={actionLoading === item.id}
                          className="flex items-center gap-1.5 bg-blue-600 hover:bg-blue-700 disabled:bg-blue-300 text-white text-xs font-bold px-4 py-2.5 rounded-xl shadow-sm transition-all">
                          {actionLoading === item.id
                            ? <Loader2 size={13} className="animate-spin" />
                            : <><Package size={13} /> Collected</>}
                        </button>
                    )}
                      {item.status === "COLLECTED" && (
                        <div className="flex flex-col gap-2">
                            <input
                                type="number"
                                min="1"
                                placeholder="Biogas kg"
                                id={`biogas-${item.id}`}
                                className="w-24 px-2 py-1.5 rounded-lg border border-gray-200 text-xs text-center focus:outline-none focus:ring-2 focus:ring-green-400"
                            />
                            <button
                                onClick={() => {
                                    const val = (document.getElementById(`biogas-${item.id}`) as HTMLInputElement)?.value;
                                    markProcessed(item.id, parseInt(val) || 1);
                                }}
                                disabled={actionLoading === item.id}
                                className="flex items-center gap-1.5 bg-green-600 hover:bg-green-700 disabled:bg-green-300 text-white text-xs font-bold px-4 py-2.5 rounded-xl shadow-sm transition-all">
                                {actionLoading === item.id
                                    ? <Loader2 size={13} className="animate-spin" />
                                    : <><Recycle size={13} /> Processed</>}
                            </button>
                        </div>
                    )}
                      {item.status === "PROCESSED" && (
                        <span className="flex items-center gap-1.5 text-xs font-bold text-green-600 bg-green-50 border border-green-200 px-3 py-2 rounded-xl">
                          <CheckCircle2 size={13} /> Done ✓
                        </span>
                      )}
                    </div>
                  </div>
                );
              })
            )}
          </div>
        </div>

        {/* Impact */}
        <div className="bg-gradient-to-r from-green-50 to-emerald-50 border border-green-100 rounded-2xl px-6 py-5 flex items-center gap-4">
          <div className="w-10 h-10 bg-green-100 rounded-xl flex items-center justify-center flex-shrink-0">
            <Leaf size={20} className="text-green-600" />
          </div>
          <div>
            <p className="text-sm font-bold text-green-800">Zero Waste Impact 🌍</p>
            <p className="text-xs text-green-600 mt-0.5">
              <span className="font-bold">{centerName}</span> has processed{" "}
              <span className="font-bold">{processed.length} batches</span> — converting food waste into biogas & compost.
            </p>
          </div>
        </div>

      </main>
    </div>
  );
}